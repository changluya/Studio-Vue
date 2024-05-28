package com.changlu.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 学院对象 school_academy
 *
 * @author 长路
 * @date 2024-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("school_academy")
@ApiModel(value="StudioGradeModel对象", description="")
public class SchoolAcademyModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 学院主键id */
    @ApiModelProperty(value = "学院主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 学院名称 */
    @ApiModelProperty(value = "学院")
    @TableField("academy_name")
    private String academyName;
}
