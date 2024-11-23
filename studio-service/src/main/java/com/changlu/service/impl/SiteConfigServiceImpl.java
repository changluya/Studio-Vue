package com.changlu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.changlu.common.config.Constants;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.Base64Util;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.SiteConfigService;
import com.changlu.service.UploadService;
import com.changlu.system.mapper.SysConfigMapper;
import com.changlu.system.pojo.SysConfig;
import com.changlu.system.service.ISysConfigService;
import com.changlu.vo.config.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @description  网站配置业务类
 * @author changlu
 * @date 2024-07-28 15:44
 */
@Service
public class SiteConfigServiceImpl implements SiteConfigService{

    private static final Logger log = LoggerFactory.getLogger(SiteConfigServiceImpl.class);

    @Autowired
    private ISysConfigService iSysConfigService;

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Autowired
    @Lazy
    private UploadService uploadService;

    /**
     * 需要进行连通性测试合集
     */
    private static final Set<String> needCheckConnTypes = new HashSet<String>(){
        {
            List<String> needs = Arrays.asList(
                    ConfigTypeEnum.SITE_UPLOAD_OSS.getConfigKey(),
                    ConfigTypeEnum.SITE_UPLOAD_FILE.getConfigKey()
            );
            this.addAll(needs);
        }
    };

    @Override
    public boolean addOrUpdateSiteConfig(ConfigVo configVo) {
        // 0、获取到最基础的id、key以及value
        Long configId = configVo.getConfigId();
        String configKey = configVo.getConfigKey();
        // 对象转为json字符串
        String configValue = JsonObjectUtil.transferObjectToJson(configVo.getConfigValue());
        // 针对需要进行连通性测试的配置来进行测试
        if (needCheckConnTypes.contains(configKey)) {
            boolean testConRes = uploadService.testConn(false, configVo);
            if (!testConRes) {
                throw new ServiceException("请检查配置，测试连通失败！");
            }
        }
        int res = 0;
        //1、根据前端传来的id是否存在来表示新增or更新
        if (configId == null) {
            //1.1、新增操作
            //若是db查询configKey不存在，则不进行重复添加
            if (sysConfigMapper.checkConfigKeyUnique(configKey) != null) {
                log.error("已添加过该configKey，无法新增！");
                throw new ServiceException("已添加过该configKey，无法新增！");
            }
            //1.2、若是枚举类不存在，则无法完成新增配置记录
            ConfigTypeEnum configTypeEnum = ConfigTypeEnum.getConfigTypeEnum(configKey);
            if (configTypeEnum == null) {
                log.error(String.format("枚举类configKey：%s不存在无法新增！", configKey));
                throw new ServiceException(String.format("枚举类configKey：%s不存在无法新增！", configKey));
            }
            SysConfig newSysConfig = SysConfig.builder()
                    .configKey(configKey)
                    .configName(configTypeEnum.getConfigName())
                    .configType(configTypeEnum.getConfigType())
                    .configValue(Base64Util.encode(configValue)).build();
            String userName = SecurityUtils.getUser().getUserName();
            newSysConfig.setCreateBy(userName);
            //1.3、db插入网站配置
            res = iSysConfigService.insertConfig(newSysConfig);
        }else {
            //1.1、更新操作
            String userName = SecurityUtils.getUser().getUserName();
            SysConfig updateSysConfig = SysConfig.builder()
                    .configId(configId)
                    .configValue(Base64Util.encode(configValue)).build();
            updateSysConfig.setUpdateBy(userName);
            res = iSysConfigService.updateConfig(updateSysConfig);
        }
        return res != 0;
    }

    @Override
    public ConfigVo selectConfigValueByConfigKey(String configKey) {
        //1、根据configKey获取枚举类，取得最终configVal转换的class
        ConfigTypeEnum configTypeEnum = ConfigTypeEnum.getConfigTypeEnum(configKey);
        Class pojoClazz = configTypeEnum.getPojoClazz();
        if (pojoClazz == null) {
            log.error(String.format("当前configKey：%s未适配pojoClazz！", configKey));
            return null;
        }
        //2、根据configKey查询配置类
        LambdaQueryWrapper<SysConfig> eqWrapper = new LambdaQueryWrapper<SysConfig>()
                .select(SysConfig::getConfigId, SysConfig::getConfigKey, SysConfig::getConfigValue)
                .eq(SysConfig::getConfigKey, configKey);
        SysConfig sysConfig = iSysConfigService.getOne(eqWrapper);
        ConfigVo configVo = new ConfigVo();
        //2.1、若是不存在，则直接返回
        if (sysConfig == null)
            return configVo;
        //2.2、若是存在，则将查询到的key、id以及配置信息构建响应
        configVo.setConfigId(sysConfig.getConfigId());
        configVo.setConfigKey(configKey);
        //获取db中的json串（此时为base64编码处理）
        String jsonStr = sysConfig.getConfigValue();
        configVo.setConfigValue(JsonObjectUtil.transferJsonToObjectByBase64(jsonStr, pojoClazz));
        return configVo;
    }

    @Override
    public ConfigVo getOpenSiteConfig(String configKey) {
        List<String> supportOpenConfigs = Arrays.stream(ConfigTypeEnum.values())
                .filter(configTypeEnum -> Constants.N.equals(configTypeEnum.getConfigType()))
                .map(ConfigTypeEnum::getConfigKey)
                .collect(Collectors.toList());
        // 若是想要获取的key为非内置的，那么就不对外开发
        if (!CollectionUtils.isEmpty(supportOpenConfigs) && !supportOpenConfigs.contains(configKey)) {
            log.error(String.format("禁止对外接口中获取内置配置，configKey => %s", configKey));
            throw new ServiceException("禁止对外接口中获取内置配置,已被拦截！");
        }
        // 获取配置参数
        return selectConfigValueByConfigKey(configKey);
    }

    // 内部获取配置接口定义
    // SITE_PARAMS_INVITE_CODE
    public String getSiteParamsInviteCode() {
        ConfigVo inviteConfigVo = selectConfigValueByConfigKey(ConfigTypeEnum.SITE_PARAMS_INVITE_CODE.getConfigKey());
        return (String) inviteConfigVo.getConfigValue();
    }


}
