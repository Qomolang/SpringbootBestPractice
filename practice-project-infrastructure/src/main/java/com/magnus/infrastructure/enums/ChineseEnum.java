package com.magnus.infrastructure.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChineseEnum {
    /**
     * 例子
     */
    EXAMPLE(0, "例子"),
    ;
    @EnumValue
    private int value;

    private String desc;

}