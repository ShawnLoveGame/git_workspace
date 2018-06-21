package com.ai.smart.bottom.enums;

/**
 * 号段配置
 */
public enum SectionEnum {

    QC_YD("重庆移动",1);

    private String name;

    SectionEnum(String name, int type) {
        this.name = name;
        this.type = type;
    }

    private int type;

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }


}
