package com.magnus.infrastructure.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 适用场景：外部系统用字符串常量当入/出参
 *
 * @author 84028
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {
    /**
     * 外部要求入参为one 或者返回为one
     */
    ONE("one"),
    ;

    private String code;

    /**
     * @param name .name()方法获得 如ONE
     * @return name对应枚举
     */
    public static CodeEnum matchByName(String name) {
        //todo 真实使用时需要对异常进行一次封装 加上业务语义
        CodeEnum output = Enum.valueOf(CodeEnum.class, name);
        return output;
    }

    public static CodeEnum matchByCode(String code) {
        return Arrays.stream(CodeEnum.values())
                //不判null或空 没人会写空枚举
                .filter(foo -> StringUtils.equals(foo.getCode(), code))
                .findFirst().orElse(null);
    }
}
