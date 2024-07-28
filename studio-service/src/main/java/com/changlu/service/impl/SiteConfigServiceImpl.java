package com.changlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.changlu.common.utils.Base64Util;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.enums.StudioRoleEnum;
import com.changlu.service.SiteConfigService;
import com.changlu.system.mapper.SysConfigMapper;
import com.changlu.system.pojo.SysConfig;
import com.changlu.system.service.ISysConfigService;
import com.changlu.vo.config.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public boolean addOrUpdateSiteConfig(ConfigVo configVo) {
        //0、获取到最基础的id、key以及value
        Long configId = configVo.getConfigId();
        String configKey = configVo.getConfigKey();
        String configValue = (String) configVo.getConfigValue();
        int res = 0;
        //1、根据id是否存在来表示新增or更新
        if (configId == null) {
            //1.1、若是configKey不存在，新增
            if (sysConfigMapper.checkConfigKeyUnique(configKey) == null) {
                ConfigTypeEnum configTypeEnum = ConfigTypeEnum.getConfigTypeEnum(configKey);
                if (configTypeEnum == null) {
                    log.error(String.format("Site相关configKey：%s不存在无法新增！", configKey));
                    return false;
                }
                SysConfig newSysConfig = SysConfig.builder()
                        .configKey(configKey)
                        .configName(configTypeEnum.getConfigName())
                        .configType(configTypeEnum.getConfigType())
                        .configValue(Base64Util.encode(configValue)).build();
                newSysConfig.setCreateBy(StudioRoleEnum.ROLE_ADMIN.getRoleKey());
                //1.2、插入网站配置
                res = iSysConfigService.insertConfig(newSysConfig);
            }
        }else {
            //1.1、若是id存在，则更新网站配置
            SysConfig updateSysConfig = SysConfig.builder()
                    .configId(configId)
                    .configValue(Base64Util.encode(configValue)).build();
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
}
