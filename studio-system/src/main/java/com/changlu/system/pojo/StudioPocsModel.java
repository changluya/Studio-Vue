package com.changlu.system.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 成果分类对象 studio_pocs
 * 
 * @author 长路
 * @date 2024-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("studio_pocs")
@ApiModel(value="StudioAchievementModel对象", description="")
public class StudioPocsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 成果名称 */
    private String pocsName;

}
