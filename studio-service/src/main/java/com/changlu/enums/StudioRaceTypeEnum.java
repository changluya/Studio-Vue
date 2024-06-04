package com.changlu.enums;

/**
 * @ClassName StudioRaceTypeEnum
 * @Author ChangLu
 * @Date 4/7/2022 5:28 PM
 * @Description 竞赛类型注解
 */
public enum StudioRaceTypeEnum {

    RACE_TYPE_OWN("个人","1"),
    RACE_TYPE_TEAM("团队","2");

    private String name;
    private String value;

    StudioRaceTypeEnum(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
