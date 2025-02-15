package com.changlu.service.impl;

import com.changlu.common.exception.ServiceCallException;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.common.utils.file.FileUploadUtils;
import com.changlu.common.utils.sm2.SM2AnnoationUtil;
import com.changlu.common.utils.spring.SpringUtils;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.service.SiteConfigService;
import com.changlu.service.UploadService;
import com.changlu.utils.file.AliyunOssUtil;
import com.changlu.utils.file.FileUpload;
import com.changlu.utils.file.FileUtil;
import com.changlu.vo.config.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private SiteConfigService siteConfigService;


    public FileUpload getFileUpload(String configKey) {
        FileUpload fileUpload = null;
        if (ConfigTypeEnum.SITE_UPLOAD_FILE.getConfigKey().equals(configKey)) {
            fileUpload = SpringUtils.getBean(FileUtil.class);
        }else if (ConfigTypeEnum.SITE_UPLOAD_OSS.getConfigKey().equals(configKey)) {
            fileUpload = SpringUtils.getBean(AliyunOssUtil.class);
        }else {
            throw new ServiceException("暂无其他文件上传配置模式！");
        }
        return fileUpload;
    }

    @Override
    public FileUpload getDefaultFileUpload() {
        // 获取当前默认的文件上传服务配置 key
        ConfigVo configVo = siteConfigService.selectConfigValueByConfigKey(ConfigTypeEnum.SITE_UPLOAD_OPTION.getConfigKey());
        String uploadOptionConfigKey = (String) configVo.getConfigValue();
        // 获取到系统默认的文件上传配置
        return this.getFileUpload(uploadOptionConfigKey);
    }

    /**
     *  若是走默认数据源配置测试连接 => isUseDefault=true
     *  若是走传来的配置测试连接    => isUseDefault=false，configValue为传输的配置参数
     * @param isUseDefault
     * @param configVo
     * @return
     */
    @Override
    public boolean testConn(boolean isUseDefault, ConfigVo configVo) {
        // 获取指定上传服务配置
        String configKey = configVo.getConfigKey();
        // 从db查询配置参数进行来进行测试连通性
        FileUpload fileUpload = this.getFileUpload(configKey);
        // 获取配置对象
        Object configValue = configVo.getConfigValue();
        // 测试连通性
        return fileUpload.testConn(isUseDefault, configValue);
    }

}
