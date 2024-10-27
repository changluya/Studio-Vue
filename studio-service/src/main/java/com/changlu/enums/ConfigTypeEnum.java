/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 15:52
 * @version 1.0
 */
package com.changlu.enums;

import com.changlu.common.config.Constants;
import com.changlu.common.config.file.AliyunOssConfig;
import com.changlu.common.config.file.LocalUploadConfig;
import com.changlu.vo.config.*;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @description  配置类别枚举类
 * @author changlu
 * @date 2024-07-28 15:53
 */
public enum ConfigTypeEnum {

    SITE_BASIC_CONFIG("网站基础配置", "site.basicConfig", Constants.N, BasicConfig.class),
    SITE_PAGE_MAIN_CONFIG("网站主页配置", "site.page.mainConfig", Constants.N, MainConfig.class),
    SITE_PAGE_FOOTER_CONFIG("网站底部栏目配置", "site.page.footerConfig", Constants.N, FooterConfig.class),
    SITE_PAGE_TIME_CONFIG("时光轴配置", "site.page.timeConfig", Constants.N, TimeConfig.class),
    SITE_PAGE_SKILL_CONFIG("技术栈配置", "site.page.skillConfig", Constants.N, SkillConfig.class),
    SITE_UPLOAD_OPTION("文件上传配置选项", "site.upload.option", Constants.Y, String.class),
    SITE_UPLOAD_FILE("本地文件上传配置", "site.upload.file", Constants.Y, LocalUploadConfig.class),
    SITE_UPLOAD_OSS("OSS资源上传配置", "site.upload.oss", Constants.Y, AliyunOssConfig.class);

    private final String configName;
    private final String configKey;
    private final String configType;
    private final Class pojoClazz;

    ConfigTypeEnum(String configName, String configKey, String configType, Class pojoClazz) {
        this.configName = configName;
        this.configKey = configKey;
        this.configType = configType;
        this.pojoClazz = pojoClazz;
    }

    /**
     * 根据配置key获取到配置类
     * @param configKey String
     * @return ConfigTypeEnum
     */
    public static ConfigTypeEnum getConfigTypeEnum(String configKey) {
        for (ConfigTypeEnum configTypeEnum : ConfigTypeEnum.values()) {
            if (configTypeEnum.configKey.equals(configKey)) {
                return configTypeEnum;
            }
        }
        return null;
    }

    public String getConfigName() {
        return configName;
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getConfigType() {
        return configType;
    }

    public Class getPojoClazz() {
        return pojoClazz;
    }
}
