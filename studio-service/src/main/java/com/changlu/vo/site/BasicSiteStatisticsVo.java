package com.changlu.vo.site;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicSiteStatisticsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 成立天数
    private Long establishmentDaysNum;

    // 团队成员数
    private Integer teamMembersNum;

    // 成果数（已收录）
    private Integer achievementsNum;

    // 证书（已收录）
    private Integer cciesNum;

    // 竞赛（已收录）
    private Integer racesNum;

}
