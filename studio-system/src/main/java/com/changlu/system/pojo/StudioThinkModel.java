package com.changlu.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.changlu.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
@TableName("Studio_think")
@ApiModel(value="StudioThinkModel对象", description="")
public class StudioThinkModel extends BaseEntity {

    @ApiModelProperty(value = "个人心得主键id")
    @TableId(value = "think_id", type = IdType.AUTO)
    private Long thinkId;

    @ApiModelProperty(value = "思考标题")
    private String thinkTitle;

    @ApiModelProperty(value = "感悟思考")
    private String content;

    @ApiModelProperty(value = "感悟思考（markdown格式）")
    private String contentMarkdown;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "用户主键id")
    private Long userId;


}
