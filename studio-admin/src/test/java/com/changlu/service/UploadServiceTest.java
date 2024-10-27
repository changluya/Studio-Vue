package com.changlu.service;

import com.changlu.StudioAdminApplication;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.utils.file.FileUpload;
import com.changlu.utils.file.FileUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StudioAdminApplication.class)
@RunWith(SpringRunner.class)
public class UploadServiceTest {

    @Autowired
    private UploadService uploadService;

    @Test
    public void testConn() {
        FileUpload fileUpload = uploadService.getFileUpload(ConfigTypeEnum.SITE_UPLOAD_FILE.getConfigKey());
        boolean flag = fileUpload.testConn(true, null);
        Assert.assertEquals(true, flag);
    }

}
