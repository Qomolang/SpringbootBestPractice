package com.magnus.infrastructure.utils;

/**
 * @author gaosong
 */
public class EnvUtils {

    public static boolean isLocalEnv() {
        return "local".equals(System.getProperty("spring.profiles.active", "unknown"));
    }

}
