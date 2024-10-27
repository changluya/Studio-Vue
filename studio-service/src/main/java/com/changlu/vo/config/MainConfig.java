/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 16:34
 * @version 1.0
 */
package com.changlu.vo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description  网站主页配置类
 * @author changlu
 * @date 2024-07-28 16:34
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    //团队描述
    private String teamDescription;

    // 我们的使命
    private String teamMission;

    // 我们的计划
    private String teamPlan;

    // 我们的愿景
    private String teamVision;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class BannerConfig implements Serializable {
        private static final long serialVersionUID = 1L;

        private String bannerImg;
        private String mainTitle;
        private String subTitle;
    }
    //banner图展示
    private List<BannerConfig> bannerTableData;
}
