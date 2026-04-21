package com.magnus.infrastructure.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author gaosong
 */
public class EnvUtils {

    public static boolean isLocalEnv() {
        return "local".equals(System.getProperty("spring.profiles.active", "unknown"));
    }

    public static boolean isTestEnv() {
        return "test".equalsIgnoreCase(System.getProperty("environment", "").trim());
    }

    // 部分场景下，居然会有 spring.profiles.active 不生效的情况，采用这个方式
    private static String envName;

    @Value("${env.name}")
    public void setEnvName(String envName) {
        EnvUtils.envName = envName;
    }

    public static boolean isTestEnvV2() {
        return "test".equalsIgnoreCase(envName);
    }

    public static boolean isStageEnvV2() {
        return "stage".equalsIgnoreCase(envName);
    }

    public static boolean isProdEnvV2() {
        return "prod".equalsIgnoreCase(envName);
    }


}
