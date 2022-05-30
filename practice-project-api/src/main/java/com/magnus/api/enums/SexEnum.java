package com.magnus.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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

}