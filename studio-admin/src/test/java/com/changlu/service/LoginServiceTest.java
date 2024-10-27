package com.changlu.service;

import com.changlu.common.utils.RsaUtil;
import com.changlu.enums.StudioRoleEnum;
import com.changlu.vo.user.req.UserAccountReqVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Test
    public void testCreateAccount() throws Exception {
        // 账号1：成员角色账号
        UserAccountReqVo account1 = UserAccountReqVo.builder()
                .username("test_member")
                .password(RsaUtil.encrypt("test_member"))
                .roleId(StudioRoleEnum.ROLE_MEMBER.value()).build();
        // 验证
        Assert.assertEquals(true, loginService.createAccount(account1));

        // 账号2：老师角色账号
        UserAccountReqVo account2 = UserAccountReqVo.builder()
                .username("test_teacher")
                .password(RsaUtil.encrypt("test_teacher"))
                .roleId(StudioRoleEnum.ROLE_TEACHER.value()).build();
        // 验证
        Assert.assertEquals(true, loginService.createAccount(account2));

        // 账号3：负责人角色账号
        UserAccountReqVo account3 = UserAccountReqVo.builder()
                .username("test_manage")
                .password(RsaUtil.encrypt("test_manage"))
                .roleId(StudioRoleEnum.ROLE_MANAGE.value()).build();
        // 验证
        Assert.assertEquals(false, loginService.createAccount(account3));
    }

}
