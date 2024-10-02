package com.changlu.task;

import com.alibaba.fastjson.JSON;
import com.changlu.service.IStudioAchievementService;
import com.changlu.web.task.GenerateTeamUsersTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class GenerateTeamUsersTaskTest {

    @Autowired
    private GenerateTeamUsersTask generateTeamUsersTask;

    @Test
    public void testDoGenerateTeamUsers() {
        List<Map<String, Object>> res = generateTeamUsersTask.doGenerateTeamUsers();
        System.out.println(res);
    }

    @Test
    public void testGenerateTeamUsers() {
        List<Map<String, Object>> res = generateTeamUsersTask.generateTeamUsers();
        System.out.println(res);
        System.out.println(JSON.toJSONString(res));
    }

}
