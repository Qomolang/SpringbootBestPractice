package com.magnus.infrastructure.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum SexEnum {
    /**
     * 男
     */
    MAN(0, "男"),
    WOMAN(1, "女"),
    ;

    @EnumValue
    private int value;

    private String desc;

    SexEnum(int value, String sex) {
    }
}