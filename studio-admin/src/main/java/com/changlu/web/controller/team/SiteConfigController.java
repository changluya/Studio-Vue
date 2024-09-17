/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 15:31
 * @version 1.0
 */
package com.changlu.web.controller.team;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.common.utils.sm2.SM2AnnoationUtil;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.service.SiteConfigService;
import com.changlu.vo.config.ConfigVo;
import com.changlu.web.env.EnvironmentContext;
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

    @Autowired
    private EnvironmentContext env;

    /**
     * 新增或编辑网站配置
     */
    @PostMapping("/addOrUpdateSiteConfig")
    @PreAuthorize("@ss.hasPerm('site:config:edit')")
    public ResponseResult addOrUpdateSiteConfig(@RequestBody ConfigVo configVo){
        if (configVo != null) {
            // 将json串转为指定的配置类
            String json = JsonObjectUtil.transferObjectToJson(configVo.getConfigValue());
            Class clazz = ConfigTypeEnum.getConfigTypeEnum(configVo.getConfigKey()).getPojoClazz();
            configVo.setConfigValue(JsonObjectUtil.transferJsonToObject(json, clazz));
            // 统一对配置类包含有sm2注解属性字段解密（其中的对象应当是指定的包含有枚举类的配置类类型）
            SM2AnnoationUtil.deserializerSM2(configVo.getConfigValue(), env.getSM2PrivateKey(), env.getSM2PublicKey());
        }
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
        if (curConfigVo != null) {
            // 统一对配置类包含有sm2注解属性字段加密
            SM2AnnoationUtil.serializeSM2(curConfigVo.getConfigValue(), env.getSM2PrivateKey(), env.getSM2PublicKey());
        }
        return ResponseResult.success(curConfigVo);
    }


}
