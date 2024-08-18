/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 15:31
 * @version 1.0
 */
package com.changlu.web.controller.team;

import com.changlu.common.domain.ResponseResult;
import com.changlu.service.SiteConfigService;
import com.changlu.vo.config.ConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @description  网站配置控制器
 * @author changlu
 * @date 2024-07-28 15:31
 */
@RestController
@RequestMapping("/api/team/site")
public class SiteConfigController {

    @Autowired
    private SiteConfigService siteConfigService;

    /**
     * 新增或编辑网站配置
     */
    @PostMapping("/addOrUpdateSiteConfig")
    @PreAuthorize("@ss.hasPerm('site:config:edit')")
    public ResponseResult addOrUpdateSiteConfig(@RequestBody ConfigVo configVo){
        boolean flag = siteConfigService.addOrUpdateSiteConfig(configVo);
        if (flag) {
            return ResponseResult.success("保存成功！");
        }else {
            return ResponseResult.error("保存或配置失败！");
        }
    }

    //查询网站配置
    @GetMapping("/selectConfigValueByConfigKey")
    @PreAuthorize("@ss.hasPerm('site:config:list')")
    public ResponseResult selectConfigValueByConfigKey(@RequestParam("configKey") String configKey){
        ConfigVo curConfigVo = siteConfigService.selectConfigValueByConfigKey(configKey);
        return ResponseResult.success(curConfigVo);
    }


}
