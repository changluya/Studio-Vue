package com.changlu.service;

import com.changlu.vo.site.BasicSiteStatisticsVo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexService
 * @Author ChangLu
 * @Date 4/9/2022 2:21 PM
 * @Description 首页数据业务层
 */
public interface SiteService {

    /**
     * 统计数据
     */
    List<Integer> getCounts();

    /**
     * 统计网站基础数据：成立天数、团队成员、网站成果数、荣誉证书数
     * @return 统计响应结果集
     */
    BasicSiteStatisticsVo getBasicSiteStatistics();

    /**
     * 统计根据年级、专业划分统计出来的竞赛、证书数据
     */
    Map<String,Object> getOptions();

    /**
     * 获取成立天数，动态计算成立以来截止到今天的天数
     * @return 成立天数
     */
    public long getEstablishmentDays();

}
