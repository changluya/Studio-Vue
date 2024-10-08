package com.changlu.web.task;

import com.changlu.common.utils.RedisCache;
import com.changlu.config.StudioConstant;
import com.changlu.service.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName IndexTask
 * @Author ChangLu
 * @Date 4/9/2022 2:29 PM
 * @Description 首页定时任务
 */
@Component
@Slf4j
public class IndexTask {

    @Autowired
    private SiteService siteService;

    @Autowired
    private RedisCache redisCache;

    //每3分钟执行一次
//    @Scheduled(initialDelay = 0, fixedRate = 3 * 60 * 1000 )
    public void syncIndexCountsAndOptions(){
        List<Integer> counts = siteService.getCounts();
        Map<String, Object> options = siteService.getOptions();
        //存储至redis
        redisCache.setCacheObject(StudioConstant.REDIS_INDEX_COUNTS, counts, 3, TimeUnit.MINUTES);
        log.info("COUNTS已存储redis：",counts);
        redisCache.setCacheObject(StudioConstant.REDIS_INDEX_OPTIONS, options, 3, TimeUnit.MINUTES);
        log.info("OPTIONS已存储redis：",options);
    }

}
