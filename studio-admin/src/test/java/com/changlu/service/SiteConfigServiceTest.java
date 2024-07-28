/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 17:54
 * @version 1.0
 */
package com.changlu.service;

import com.alibaba.fastjson.JSONObject;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.vo.config.BasicConfig;
import com.changlu.vo.config.ConfigVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description  SiteConfig业务类单测
 * @author changlu
 * @date 2024-07-28 17:54
 */
@SpringBootTest
public class SiteConfigServiceTest {

    @Autowired
    private SiteConfigService siteConfigService;

    @Test
    public void testJSONToPojo() {
        String json = "{\n" +
                "        siteTitle: '仿生实验室',  \n" +
                "        teamTitle: '仿生实验室',  \n" +
                "        teamLogo: 'http://pictured-bedtest.oss-cn-beijing.aliyuncs.com/test/studio/8c44b79e-cd18-40ae-8d5a-5e2a0df303b7.png',\n" +
                "        ISPN: '京公网安备11000002000001号', \n" +
                "        siteCreateTime: '2016'\n" +
                "      }";
        BasicConfig basicConfig = JSONObject.parseObject(json, BasicConfig.class);
        System.out.println(basicConfig);
    }

    @Test
    public void addOrUpdateSiteConfigTest() {
        System.out.println("===========新增配置===============");
        //新增配置
        ConfigVo configVo = new ConfigVo();
        configVo.setConfigKey(ConfigTypeEnum.SITE_BASICCONFIG.getConfigKey());
        String json = "{\n" +
                "        siteTitle: '仿生实验室',  \n" +
                "        teamTitle: '仿生实验室',  \n" +
                "        teamLogo: 'http://pictured-bedtest.oss-cn-beijing.aliyuncs.com/test/studio/8c44b79e-cd18-40ae-8d5a-5e2a0df303b7.png',\n" +
                "        ISPN: '京公网安备11000002000001号', \n" +
                "        siteCreateTime: '2016'\n" +
                "      }";
        configVo.setConfigValue(json);
        System.out.println(siteConfigService.addOrUpdateSiteConfig(configVo));
        System.out.println("===========查询配置===============");

        //查询该配置
        System.out.println("===========查询配置===============");
        String configKey = ConfigTypeEnum.SITE_BASICCONFIG.getConfigKey();
        ConfigVo queryConfigVo = siteConfigService.selectConfigValueByConfigKey(configKey);
        System.out.println(queryConfigVo);
        System.out.println("===========查询配置===============");

        //更新配置
        System.out.println("===========更新配置===============");
        json = "{\n" +
                "        siteTitle: '仿生实验室1',  \n" +
                "        teamTitle: '仿生实验室1',  \n" +
                "        teamLogo: 'http://pictured-bedtest.oss-cn-beijing.aliyuncs.com/test/studio/8c44b79e-cd18-40ae-8d5a-5e2a0df303b7.png',\n" +
                "        ISPN: '京公网安备11000002000001号', \n" +
                "        siteCreateTime: '2017'\n" +
                "      }";
        configVo.setConfigValue(json);
        configVo.setConfigId(queryConfigVo.getConfigId());
        System.out.println(siteConfigService.addOrUpdateSiteConfig(configVo));
        System.out.println("===========更新配置===============");
    }


}
