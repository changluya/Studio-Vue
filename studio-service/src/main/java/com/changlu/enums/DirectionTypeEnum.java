package com.changlu.enums;

/**
 * 去向类别枚举
 */
public enum DirectionTypeEnum {

    ENROLLED(1, "在校"),
    PROMOTION(2, "升学"),
    EMPLOYMENT(3, "就业");

    public Integer directionType;
    public String directionTypeName;

    DirectionTypeEnum(Integer directionType, String directionTypeName) {
        this.directionType = directionType;
        this.directionTypeName = directionTypeName;
    }

    public static String getDirectionTypeName(Integer directionType) {
        if (directionType == null) {
            return ENROLLED.directionTypeName;
        }
        for (DirectionTypeEnum value : values()) {
            if (value.directionType.equals(directionType)) {
                return value.directionTypeName;
            }
        }
        return ENROLLED.directionTypeName;
    }

}
