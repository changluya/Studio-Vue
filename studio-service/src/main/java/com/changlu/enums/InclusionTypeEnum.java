package com.changlu.enums;

public enum InclusionTypeEnum {
    NO_INCLUSION(0, "未收录"),
    APPLY_INCLUSION(1, "申请收录"),
    REFUSE_INCLUSION(2, "拒绝收录"),
    ALREADY_INCLUSION(3, "已收录");

    private Integer val;
    private String inclusionName;

    InclusionTypeEnum(Integer val, String inclusionName) {
        this.val = val;
        this.inclusionName = inclusionName;
    }

    public Integer getVal() {
        return val;
    }

    public String getInclusionName() {
        return inclusionName;
    }
}
