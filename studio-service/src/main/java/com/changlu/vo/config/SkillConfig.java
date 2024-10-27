package com.changlu.vo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description  展示技术栈类
 * @author changlu
 * @date 2024-09-15 18:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    // 技能名称
    private String skillName;

    // 技能百分比
    private Integer skillPercentage;

}
