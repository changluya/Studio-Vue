package com.changlu.annoation;

import com.changlu.StudioAdminApplication;
import com.changlu.common.config.file.AliyunOssConfig;
import com.changlu.common.utils.sm2.SM2AnnoationUtil;
import com.changlu.web.env.EnvironmentContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StudioAdminApplication.class)
@RunWith(SpringRunner.class)
public class SM2Test {

    @Autowired
    private EnvironmentContext env;

    @Test
    public void testSM2Annoation() {
        AliyunOssConfig ossConfig = AliyunOssConfig.builder()
                .endpoint("https://oss-cn-hangzhou.aliyuncs.com")
                .accessKeyId("dafsaddsda")
                .accessKeySecret("adfaasds")
                .bucketName("cn-hangzhou")
                .key("1232").build();
        String privateKey = env.getSM2PrivateKey();
        String publicKey = env.getSM2PublicKey();
        // 加密测试
        SM2AnnoationUtil.serializeSM2(ossConfig, privateKey, publicKey);
        System.out.println(ossConfig);
        // 解密测试
        SM2AnnoationUtil.deserializerSM2(ossConfig, privateKey, publicKey);
        System.out.println(ossConfig);
    }

}
