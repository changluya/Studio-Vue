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
 * <p>
 *
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("school_grade")
@ApiModel(value="StudioGradeModel对象", description="")
public class SchoolGradeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "年级主键id")
    @TableId(value = "grade_id", type = IdType.AUTO)
    private Long gradeId;

    @ApiModelProperty(value = "年级")
    @TableField("grade_num")
    private Integer gradeNum;


}
