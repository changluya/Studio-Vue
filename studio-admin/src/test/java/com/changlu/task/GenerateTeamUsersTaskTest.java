package com.changlu.task;

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
        List<Map> res = generateTeamUsersTask.doGenerateTeamUsers();
        System.out.println(res);
    }
}
