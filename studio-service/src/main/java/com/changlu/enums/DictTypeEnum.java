package com.changlu.enums;

public enum DictTypeEnum {

    CCIE_TYPE("证书专业资质类别", "CCIE_TYPE");

    private String dictName;
    private String dictType;

    DictTypeEnum(String dictName, String dictType) {
        this.dictName = dictName;
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public String getDictType() {
        return dictType;
    }
}
