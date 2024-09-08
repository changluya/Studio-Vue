package com.changlu.vo.achievement.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementReqVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 成果分类id
    private Long pocsId;

    // 搜索年份
    private String searchYear;
}
