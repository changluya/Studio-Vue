package com.changlu.vo.achievement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowAchievement implements Serializable {

    private static final long serialVersionUID = 1L;

    // 来源StudioAchievementModel
    /** 标题 */
    private String title;

    /** 预览图 */
    private String previewImg;

    /** 过程开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 过程结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    // 来源StudioAchievementDTO
    // 参与成员
    private String partUserNames;

    // 前端展示内容
    // 成果过程时间追溯
    private String procedureDate;

    // 当填充完数据之后手动进行build构建下
    public void build() {
        String startTimeStr = DateFormatUtils.format(startTime, "yyyy.MM.dd");
        String endTimeStr = DateFormatUtils.format(endTime, "yyyy.MM.dd");
        // 构建procedureDate
        this.setProcedureDate(String.format("%s~%s",startTimeStr , endTimeStr));
    }

}
