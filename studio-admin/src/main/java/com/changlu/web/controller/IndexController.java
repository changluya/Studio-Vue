package com.changlu.web.controller;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.utils.RedisCache;
import com.changlu.service.SiteService;
import com.changlu.web.env.EnvironmentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Author ChangLu
 * @Date 4/9/2022 9:14 AM
 * @Description 首页控制器
 */
@RestController
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SiteService siteService;

    @Autowired
    private EnvironmentContext env;

    @GetMapping("/counts")
//    @PreAuthorize("@ss.hasRole('member')")
    public ResponseResult getCounts(){
        List<Integer> counts = siteService.getCounts();
        return ResponseResult.success(counts);
    }


    @GetMapping("/options")
//    @PreAuthorize("@ss.hasRole('member')")
    public ResponseResult getOptions(){
        Map<String, Object> options = siteService.getOptions();
        return ResponseResult.success(options);
    }

    @GetMapping("/getSM2PublicKey")
    public ResponseResult getSM2PublicKey(){
        return ResponseResult.success(env.getSM2PublicKey());
    }

}
