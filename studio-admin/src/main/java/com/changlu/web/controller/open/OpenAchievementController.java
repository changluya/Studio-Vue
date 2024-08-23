package com.changlu.web.controller.open;

import com.changlu.common.domain.ResponseResult;
import com.changlu.config.StudioConstant;
import com.changlu.service.IStudioAchievementService;
import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import com.changlu.vo.achievement.ShowAchievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName OpenAchievementController
 * @Author ChangLu
 * @Date 8/22/2024 13:02 AM
 * @Description 开放网站成果控制器
 */
@RestController
@RequestMapping("/api/open/achievement")
public class OpenAchievementController {

    @Autowired
    private IStudioAchievementService studioAchievementService;

    @GetMapping("/show")
    public ResponseResult showAchievements(StudioAchievementModel studioAchievementModel){
        List<ShowAchievement> result = studioAchievementService.showAchievements(studioAchievementModel);
        return ResponseResult.success(result);
    }

}
