package com.changlu.env;

import com.changlu.StudioAdminApplication;
import com.changlu.web.env.EnvironmentContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StudioAdminApplication.class)
@RunWith(SpringRunner.class)
public class EnvironmentContextTest {

    @Autowired
    private EnvironmentContext env;

    @Test
    public void testGetSM2() {
        // 环境变量读取配置
        String sm2PublicKey = env.getSM2PublicKey();
        String sm2PrivateKey = env.getSM2PrivateKey();
        System.out.println("sm2PublicKey:" + sm2PublicKey);
        System.out.println("sm2PrivateKey:" + sm2PrivateKey);
        Assert.assertNotNull(sm2PublicKey);
        Assert.assertNotNull(sm2PublicKey);
    }

}
