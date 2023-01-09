package com.magnus.excel.infra.utils;

import com.google.common.base.Preconditions;
import com.magnus.excel.infra.enums.ExcelActionEnum;
import com.magnus.excel.infra.enums.ExcelFlagEnum;
import com.magnus.excel.infra.enums.ExcelSceneEnum;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RedisKeyFactory {

    public static String buildPrefix(Enum... prefix) {
        for (Enum arg : prefix) {
            Preconditions.checkNotNull(arg);
        }

        String result = Arrays.stream(prefix)
                .map(Enum::name)
                .collect(Collectors.joining("_"));

        return result;
    }

    public static String buildRedisFactor(String... factors) {
        for (String arg : factors) {
            Preconditions.checkNotNull(arg);
        }

        String result = String.join("_", factors);
        return result;
    }

    public static String buildRedisKey(ExcelSceneEnum scene, ExcelActionEnum action, ExcelFlagEnum flag, String... args) {
        Preconditions.checkNotNull(scene);

        String prefix = buildPrefix(scene, action, flag);
        String factor = buildRedisFactor(args);

        String result = prefix + ":" + factor;
        return result;
    }

}
