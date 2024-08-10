package com.changlu.web.controller.open.site;

import com.changlu.common.domain.ResponseResult;
import com.changlu.service.SiteConfigService;
import com.changlu.vo.config.ConfigVo;
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

    @GetMapping("/config")
    public ResponseResult getSiteConfig(@RequestParam("configKey") String configKey){
        ConfigVo curConfigVo = siteConfigService.selectConfigValueByConfigKey(configKey);
        return ResponseResult.success(curConfigVo);
    }

}
