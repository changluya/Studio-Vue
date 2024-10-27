package com.changlu.system.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.changlu.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 成果对象 studio_achievement
 * 
 * @author changlu
 * @date 2024-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("studio_achievement")
@ApiModel(value="StudioAchievementModel对象", description="")
public class StudioAchievementModel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 标题 */
    private String title;

    /** 预览图 */
    private String previewImg;

    /** 成果分类id【studio_pocs】 */
    private Long pocsId;

    /** 创建用户id【sys_user】 */
    private Long createUserId;

    /** 参与者，包含有多个用户id【sys_user】，使用,分隔 */
    private String partUserIds;

    /** 过程开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 过程结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 成果描述及总结材料（可以markdown形式） */
    private String description;

    /** 删除标志，0表示未删除，1表示删除 */
    private Integer delFlag;

    /** 收录标志，0表示未收录，1为申请收录，2为收录打回，3为收录通过（可对外展示） */
    private Integer inclusionFlag;

}
