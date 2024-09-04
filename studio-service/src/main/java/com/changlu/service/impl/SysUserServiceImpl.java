package com.changlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.changlu.common.utils.StringUtils;
import com.changlu.domain.LoginUser;
//需要web的TokenService
import com.changlu.enums.UserStatusEnum;
import com.changlu.security.service.TokenService;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.ISysUserService;
import com.changlu.system.mapper.SysMenuMapper;
import com.changlu.system.mapper.SysUserMapper;
import com.changlu.system.pojo.StudioRaceModel;
import com.changlu.system.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SysUserServiceImpl
 * @Author ChangLu
 * @Date 4/2/2022 7:23 PM
 * @Description 用户业务层
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private TokenService tokenService;

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 更新系统用户
     * @param sysUser 系统用户更新的信息
     * @param updateOwn 是否是更新自己
     * @return
     */
    @Override
    public int updateSysUser(SysUser sysUser, Boolean updateOwn) {
        //设置当前更新用户的id、更新时间
        if (ObjectUtils.isEmpty(sysUser.getUserId())) {
            sysUser.setUserId(SecurityUtils.getUserId());
        }
        sysUser.setUpdateTime(new Date());
        //1、更新数据库用户信息
        int result = sysUserMapper.updateSysUser(sysUser);
        //2、更新redis用户信息
        if (result > 0){
            //若是更新用户本身则更新redis、非用户本身不做更新（指的是管理员对某个用户更新信息）
            if (updateOwn) {
                //走数据库，查询一遍最新的用户信息、权限不做查询依旧使用原来的（因为管理员再进行对账号授权时，不会修改对应的缓存）
                SysUser user = sysUserMapper.loadUserByUsername(SecurityUtils.getUser().getUserName());
                List<String> perms = new ArrayList<>();;
                if (user.isAdmin()){
                    perms.add("*:*:*");
                }else{
                    perms = sysMenuMapper.selectPermsByUserId(user.getUserId());
                }
                //获取最新的权限信息
//                List<String> permissions = sysMenuMapper.selectPermsByUserId(user.getUserId());
                LoginUser newLoginUser = new LoginUser(user, perms);
                newLoginUser.setToken(SecurityUtils.getLoginUser().getToken());//token(uuid)依旧是当前用户自身的
                tokenService.refreshToken(newLoginUser);
            }
        }
        return result;
    }

    @Override
    public List<SysUser> selectAllocatedList(SysUser user) {
        return sysUserMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return sysUserMapper.selectUnallocatedList(user);
    }

    @Override
    public Map<Long, SysUser> selectUserMap(Long[] userIds) {
        // 去重用户id，查询用户集合
        List<SysUser> users = sysUserMapper.selectUserByIds(Arrays.stream(userIds).distinct().toArray(Long[]::new));
        // 构建用户map映射集合
        Map<Long, SysUser> userMap = users.stream()
                .collect(Collectors.toMap(SysUser::getUserId, user -> user));
        return userMap;
    }

    @Override
    public Map<String, String> selectpartUsersRealNameMap(String[] partUserStrArr) {
        if (partUserStrArr == null || partUserStrArr.length == 0) {
            return Collections.emptyMap();
        }
        // 收集所有参与用户包含的所有用户id
        Long[] uids = Arrays.stream(partUserStrArr)
                .filter(StringUtils::isNotEmpty)
                .flatMap((partUserStr -> Arrays.stream(partUserStr.split(","))))
                .map((Long::parseLong))
                .toArray(Long[]::new);
        // 构建用户map集合
        Map<Long, SysUser> userMap = selectUserMap(uids);
        // 结果集
        Map<String, String> partUsersRealNameMap = new HashMap<>();
        // 遍历所有的用户组字符串
        for (String partUserStr : partUserStrArr) {
            List<String> partUserIds = Arrays.asList(partUserStr.split(","));
            String partUsersName = partUserIds.stream()
                    .filter(StringUtils::isNotEmpty)
                    .mapToLong(Long::parseLong)
                    .mapToObj((uid) -> userMap.get(uid) != null ? userMap.get(uid).getRealName() : null)
                    .filter(Objects::nonNull) // 过滤掉null值
                    .collect(Collectors.joining(", "));
            partUsersRealNameMap.put(partUserStr, partUsersName);
        }
        return partUsersRealNameMap;
    }

    @Override
    public int countTeamUser() {
        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, UserStatusEnum.ACTIVE.value())
                .ne(SysUser::getUserId, 1L);//系统管理员不计入总数
        int userCount = sysUserMapper.selectCount(userWrapper);
        return userCount;
    }

}
