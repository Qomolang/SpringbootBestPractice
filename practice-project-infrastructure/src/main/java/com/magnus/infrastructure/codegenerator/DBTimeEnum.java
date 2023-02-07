package com.magnus.infrastructure.codegenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 适用场景：外部系统用字符串常量当入/出参
 *
 * @author gaosong
 */
@Getter
@AllArgsConstructor
public enum DBTimeEnum {
    /**
     * 创建时间
     */
    CreateTime("gmtCreate"),
    /**
     * 修改时间
     */
    UpdateTIme("gmtModified"),

    ;

    private String code;

    /**
     * @param name .name()方法获得 如ONE
     * @return name对应枚举
     */
    public static DBTimeEnum matchByName(String name) {
        //todo 真实使用时需要对异常进行一次封装 加上业务语义
        DBTimeEnum output = Enum.valueOf(DBTimeEnum.class, name);
        return output;
    }

    public static DBTimeEnum matchByCode(String code) {
        return Arrays.stream(DBTimeEnum.values())
                //不判null或空 没人会写空枚举
                .filter(foo -> StringUtils.equals(foo.getCode(), code))
                .findFirst().orElse(null);
    }
}
