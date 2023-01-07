package com.magnus.excel.infra.utils;

import com.google.common.base.Preconditions;
import com.magnus.excel.infra.common.enums.ExcelActionEnum;
import com.magnus.excel.infra.common.enums.ExcelSceneEnum;

import java.util.Arrays;

public class RedisKeyFactory {

    public static String buildRedisFactor(String... factors) {
        for (String arg : factors) {
            Preconditions.checkNotNull(arg);
        }

        String result = String.valueOf(Arrays.stream(factors).reduce((a, b) -> a + "_" + b));
        return result;
    }

    public static String buildRedisKey(ExcelSceneEnum scene, ExcelActionEnum action, String... args) {
        Preconditions.checkNotNull(scene);

        String factor = buildRedisFactor(args);

        String result = scene.name() + ":" + action.name() + ":" + factor;
        return result;
    }

}
