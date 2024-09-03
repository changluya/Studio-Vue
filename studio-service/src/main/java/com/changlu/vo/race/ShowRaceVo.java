package com.changlu.vo.race;
import com.baomidou.mybatisplus.annotation.TableId;
import com.changlu.common.annoation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowRaceVo implements Serializable {
    private static final long serialVersionUID = 1L;

    // 来源于StudioCcieModel
    @ApiModelProperty(value = "竞赛主键id")
    private Long raceId;
    @ApiModelProperty(value = "竞赛名称")
    private String raceName;
    @ApiModelProperty(value = "竞赛成员（存储参与成员的id，使用,分割）")
    private String raceMembers;
    @ApiModelProperty(value = "竞赛获奖证书")
    private String raceCcie;
    @ApiModelProperty(value = "竞赛结束时间")
    @JsonFormat(pattern="yyyy年MM月dd日")
    private Date raceEndTime;
    @ApiModelProperty(value = "标识个人还是团队(1是个人,2是团队)")
    private String raceFlag;

    // 额外处理参数
    // 参与者姓名
    private String teamMemberRealNames;

}
