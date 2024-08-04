package com.changlu.service;

import com.changlu.vo.InfoVo;
import com.changlu.vo.config.ConfigVo;
import org.apache.poi.ss.formula.functions.T;

/**
 * @description  网站配置业务层
 * @author changlu
 * @date 2024-07-28 15:39
 */
public interface SiteConfigService {

    boolean addOrUpdateSiteConfig(ConfigVo configVo);

    ConfigVo selectConfigValueByConfigKey(String configKey);

}