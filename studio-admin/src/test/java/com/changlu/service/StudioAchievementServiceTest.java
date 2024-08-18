package com.changlu.service;

import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import com.changlu.system.service.IStudioAchievementService;
import com.changlu.system.service.impl.StudioAchievementServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StudioAchievementServiceTest {

    @Autowired
    private IStudioAchievementService studioAchievementService;

    @Test
    public void testSelectStudioAchievementList() {
        List<StudioAchievementDTO> studioAchievementDTOS = studioAchievementService.selectStudioAchievementList(null);
        System.out.println(studioAchievementDTOS);
    }

}
