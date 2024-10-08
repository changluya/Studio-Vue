package com.changlu.enums;

/**
 * @ClassName StudioRoleEnum
 * @Author ChangLu
 * @Date 4/6/2022 8:44 AM
 * @Description 工作室身份角色枚举
 */
public enum StudioRoleEnum {

    /**
     * ROLE_ADMIN:系统管理员，角色id为1
     * ROLE_MANAGE:负责人，角色id为2
     * ROLE_MEMBER:成员，角色id为3
     * ROLE_PAST_MEMBER：历届负责人(能够对当前的工作室信息进行查看),角色id为4
     * ROLE_TEACHER：指导老师，角色id为5
     */
    ROLE_ADMIN("管理员", 1L, "admin"),
    ROLE_MANAGE("负责人", 2L, "manage"),
    ROLE_MEMBER("成员", 3L, "member"),
    ROLE_PAST_MANAGE("历届负责人", 4L, "pastmanage"),
    ROLE_TEACHER("指导老师", 5L, "teacher");

    private String name;
    private Long roleId;
    private String roleKey;

    StudioRoleEnum(String name, Long roleId){
        this.name = name;
        this.roleId = roleId;
    }

    StudioRoleEnum(String name, Long roleId, String roleKey){
        this.name = name;
        this.roleId = roleId;
        this.roleKey = roleKey;
    }

    public Long value(){
        return this.roleId;
    }

    public String getName(){
        return this.name;
    }

    public String getRoleKey(){
        return this.roleKey;
    }
}
