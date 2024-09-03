package com.changlu.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest
public class SysUserServiceTest {

    @Autowired
    private ISysUserService iSysUserService;

    @Test
    public void selectpartUsersRealNameMap() {
        String partUserStr1 = "35,37,39";
        String partUserStr2 = "45,46,47";
        String partUserStr3 = ""; //空字符串情况
        String[] partUserStrArr = Arrays.asList(partUserStr1, partUserStr2, partUserStr3).toArray(new String[0]);
        Map<String, String> partUsersRealNameMap = iSysUserService.selectpartUsersRealNameMap(partUserStrArr);
        Assert.assertEquals(partUsersRealNameMap.get(partUserStr1), "迪迦, 小智, 蜡笔小新");
        Assert.assertEquals(partUsersRealNameMap.get(partUserStr2), "铁甲小宝1, 一休, 长路");
        Assert.assertEquals(partUsersRealNameMap.get(partUserStr3), "");
    }

}
