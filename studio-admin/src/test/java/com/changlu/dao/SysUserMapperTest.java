package com.changlu.dao;

import com.changlu.system.mapper.SysUserMapper;
import com.changlu.system.pojo.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import org.junit.*;
import java.util.List;

@SpringBootTest
public class SysUserMapperTest {

    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    public void testSelectUserByIds() {
        Long[] ids = {1L, 30L, 31L};
        List<SysUser> users = sysUserMapper.selectUserByIds(ids);
        Assert.assertEquals(ids.length, users.size());
    }

}
