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
import com.changlu.vo.config.MainConfig;
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
        String json ="{\n" +
                "        teamDescription: '这里是物联网工作室，在这里不仅有学习硬件，还有学习软件的小伙伴们，我们都在前进的路上，未来值得期待！\n'工作室有着丰富的学习资源，有着可以帮助你解决问题的学长学姐们以及专业指导老师，让你不断在专业领域进行探索和挖掘知识宝藏。" +
                "        bannerTableData: [{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/071.png',\n" +
                "          mainTitle: '欢迎来到仿生实验室1',\n" +
                "          subTitle: '一群志同道合的人，一起奔跑在理想的路上...'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/072.png',\n" +
                "          mainTitle: '关于我们1',\n" +
                "          subTitle: '最好的团队，最好的我们，不负韶华，努力奋斗。'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/073.png',\n" +
                "          mainTitle: '时光轴1',\n" +
                "          subTitle: '时间是温柔的羽毛，把过往的灰尘轻轻弹去。'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/074.png',\n" +
                "          mainTitle: '团队'1,\n" +
                "          subTitle: '拍照只需要三秒，可锁住的是我们三年青春，感谢遇见！'\n" +
                "        }]\n" +
                "      }";
        MainConfig mainConfig = JSONObject.parseObject(json, MainConfig.class);
        System.out.println(mainConfig);
    }

    //网站基础配置:site.basicConfig
    @Test
    public void addOrUpdateSite_basicConfig_Test() {
        String createJson = "{\n" +
                "        siteTitle: '仿生实验室1',  \n" +
                "        teamTitle: '仿生实验室1',  \n" +
                "        teamLogo: 'http://pictured-bedtest.oss-cn-beijing.aliyuncs.com/test/studio/8c44b79e-cd18-40ae-8d5a-5e2a0df303b7.png',\n" +
                "        ISPN: '京公网安备11000002000001号', \n" +
                "        siteCreateTime: '2016'\n" +
                "      }";
        String updateJson = "{\n" +
                "        siteTitle: '仿生实验室',  \n" +
                "        teamTitle: '仿生实验室',  \n" +
                "        teamLogo: 'http://pictured-bedtest.oss-cn-beijing.aliyuncs.com/test/studio/8c44b79e-cd18-40ae-8d5a-5e2a0df303b7.png',\n" +
                "        ISPN: '京公网安备11000002000001号', \n" +
                "        siteCreateTime: '2017'\n" +
                "      }";
        String configKey = ConfigTypeEnum.SITE_BASIC_CONFIG.getConfigKey();
        addOrUpdateSiteConfigTest(createJson, updateJson, configKey);
    }

    //网站主页:site.page.mainConfig
    @Test
    public void addOrUpdateSite_page_mainConfig_Test() {
        String createJson = "{\n" +
                "        teamDescription: '这里是物联网工作室，在这里不仅有学习硬件，还有学习软件的小伙伴们，我们都在前进的路上，未来值得期待！\n'工作室有着丰富的学习资源，有着可以帮助你解决问题的学长学姐们以及专业指导老师，让你不断在专业领域进行探索和挖掘知识宝藏。" +
                "        bannerTableData: [{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/071.png',\n" +
                "          mainTitle: '欢迎来到仿生实验室1',\n" +
                "          subTitle: '一群志同道合的人，一起奔跑在理想的路上...'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/072.png',\n" +
                "          mainTitle: '关于我们1',\n" +
                "          subTitle: '最好的团队，最好的我们，不负韶华，努力奋斗。'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/073.png',\n" +
                "          mainTitle: '时光轴1',\n" +
                "          subTitle: '时间是温柔的羽毛，把过往的灰尘轻轻弹去。'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/074.png',\n" +
                "          mainTitle: '团队'1,\n" +
                "          subTitle: '拍照只需要三秒，可锁住的是我们三年青春，感谢遇见！'\n" +
                "        }]\n" +
                "      }";
        String updateJson = "{\n" +
                "        teamDescription: '这里是物联网工作室，在这里不仅有学习硬件，还有学习软件的小伙伴们，我们都在前进的路上，未来值得期待！\n'工作室有着丰富的学习资源，有着可以帮助你解决问题的学长学姐们以及专业指导老师，让你不断在专业领域进行探索和挖掘知识宝藏。" +
                "        bannerTableData: [{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/071.png',\n" +
                "          mainTitle: '欢迎来到仿生实验室1',\n" +
                "          subTitle: '一群志同道合的人，一起奔跑在理想的路上...'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/072.png',\n" +
                "          mainTitle: '关于我们1',\n" +
                "          subTitle: '最好的团队，最好的我们，不负韶华，努力奋斗。'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/073.png',\n" +
                "          mainTitle: '时光轴1',\n" +
                "          subTitle: '时间是温柔的羽毛，把过往的灰尘轻轻弹去。'\n" +
                "        },{\n" +
                "          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/074.png',\n" +
                "          mainTitle: '团队'1,\n" +
                "          subTitle: '拍照只需要三秒，可锁住的是我们三年青春，感谢遇见！'\n" +
                "        }]\n" +
                "      }";
        String configKey = ConfigTypeEnum.SITE_PAGE_MAIN_CONFIG.getConfigKey();
        addOrUpdateSiteConfigTest(createJson, updateJson, configKey);
    }



    public void addOrUpdateSiteConfigTest(Object creatConfigValue, Object updateConfigValue, String configKey) {
        System.out.println("===========1、新增配置===============");
        //新增配置
        ConfigVo configVo = new ConfigVo();
        configVo.setConfigKey(configKey);
        configVo.setConfigValue(creatConfigValue);
        System.out.println(siteConfigService.addOrUpdateSiteConfig(configVo));
        System.out.println("===========新增配置===============");

        //查询该配置
        System.out.println("===========查询配置===============");
        ConfigVo queryConfigVo = siteConfigService.selectConfigValueByConfigKey(configKey);
        System.out.println(queryConfigVo);
        System.out.println("===========查询配置===============");

        //更新配置
        System.out.println("===========更新配置===============");
        configVo.setConfigValue(updateConfigValue);
        configVo.setConfigId(queryConfigVo.getConfigId());
        System.out.println(siteConfigService.addOrUpdateSiteConfig(configVo));
        System.out.println("===========更新配置===============");

        //查询该配置
        System.out.println("===========查询配置===============");
        queryConfigVo = siteConfigService.selectConfigValueByConfigKey(configKey);
        System.out.println(queryConfigVo);
        System.out.println("===========查询配置===============");
    }


}
