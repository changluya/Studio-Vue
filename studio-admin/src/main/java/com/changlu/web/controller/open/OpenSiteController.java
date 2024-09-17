package com.changlu.web.controller.open;

import com.changlu.common.domain.ResponseResult;
import com.changlu.service.SiteConfigService;
import com.changlu.service.SiteService;
import com.changlu.vo.config.ConfigVo;
import com.changlu.vo.site.BasicSiteStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OpenSiteController
 * @Author ChangLu
 * @Date 8/9/2024 21:48 PM
 * @Description 开放网站配置控制器：网站获取配置
 */
@RestController
@RequestMapping("/api/open/site")
public class OpenSiteController {

    @Autowired
    private SiteConfigService siteConfigService;

    @Autowired
    private SiteService siteService;

    @GetMapping("/statistics")
    public ResponseResult getSiteBasicStatistics(){
        BasicSiteStatisticsVo basicSiteStatistics = siteService.getBasicSiteStatistics();
        return ResponseResult.success(basicSiteStatistics);
    }


    @GetMapping("/config")
    public ResponseResult getSiteConfig(@RequestParam("configKey") String configKey){
        ConfigVo curConfigVo = siteConfigService.selectConfigValueByConfigKey(configKey);
        // 针对敏感字段内容进行加密处理
        return ResponseResult.success(curConfigVo);
    }

}
