package com.changlu.system.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.changlu.system.pojo.StudioAchievementModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class StudioAchievementDTO extends StudioAchievementModel {

    private String pocsName;

    // 创建者姓名，根据【createUserId】构建
    private String createUserName;

    // 参与成员姓名，根据【partUserIds】构建
    private String partUserNames;

}
