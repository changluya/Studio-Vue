/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 15:52
 * @version 1.0
 */
package com.changlu.enums;

import com.changlu.vo.config.BasicConfig;
import com.changlu.vo.config.FooterConfig;
import com.changlu.vo.config.MainConfig;
import com.changlu.vo.config.TimeConfig;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @description  配置类别枚举类
 * @author changlu
 * @date 2024-07-28 15:53
 */
public enum ConfigTypeEnum {

    SITE_BASICCONFIG("网站基础配置", "site.basicConfig", "N", BasicConfig.class),
    SITE_PAGE_MAINCONFIG("网站主页", "site.page.mainConfig", "N", MainConfig.class),
    SITE_PAGE_FOOTERCONFIG("网站底部栏目", "site.page.footerConfig", "N", FooterConfig.class),
    SITE_PAGE_TIMECONFIG("时光轴", "site.page.timeConfig", "N", TimeConfig.class),
    SITE_UPLOAD_FILE("本地文件上传配置", "site.upload.file", "N", null),
    SITE_UPLOAD_OSS("oss资源配置", "site.upload.oss", "N", null);

    private String configName;
    private String configKey;
    private String configType;
    private Class pojoClazz;

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
