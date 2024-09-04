package com.changlu.service;

import com.changlu.system.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ISysUserService
 * @Author ChangLu
 * @Date 4/2/2022 7:22 PM
 * @Description 系统用户业务层接口
 */
public interface ISysUserService {

    /**
     * 更新系统用户
     * @param sysUser 系统用户对象
     * @return
     */
    int updateSysUser(SysUser sysUser, Boolean updateOwn);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /***
     * 根据用户id来构建用户map集合，key为用户id、value为用户实体
     * @param userIds 用户ids集合 {1, 2, 3, 4, 5}
     * @return
     */
    public Map<Long, SysUser> selectUserMap(Long[] userIds);

    /***
     * 根据用户参与者字符串来构建用户map集合
     * @param partUserStrArr 参与用户组字符串 {"1,2,3", "4,5,6"}
     * @return key为"1,2,3" => "茅津菁,长路,蜡笔小新"
     */
    public Map<String, String> selectpartUsersRealNameMap(String[] partUserStrArr);

    /**
     * 统计团队所有人数
     * @return 人数
     */
    public int countTeamUser();
}
