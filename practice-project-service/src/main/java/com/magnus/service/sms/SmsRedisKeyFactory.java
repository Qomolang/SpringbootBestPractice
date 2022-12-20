package com.magnus.service.sms;

import com.google.common.base.Preconditions;
import com.magnus.service.sms.enums.SmsTemplateEnum;


import java.util.Arrays;

public class SmsRedisKeyFactory {

    public static String buildRedisFactor(String... factors) {
        for (String arg : factors) {
            Preconditions.checkNotNull(arg);
        }

        String result = String.valueOf(Arrays.stream(factors).reduce((a, b) -> a + "_" + b));
        return result;
    }

    public static String buildMobileDailyUpperLimitKey(SmsTemplateEnum template, String factor) {
        return buildRedisKey(template, "MobileDailyUpperLimitCheck", factor);
    }

    public static String buildSendSmsLimitKey(SmsTemplateEnum template, String factor) {
        return buildRedisKey(template, "SendSms", factor);
    }

    public static String buildRedisKey(SmsTemplateEnum template, String action, String factor) {
        Preconditions.checkNotNull(template);

        String result = template.getBiz().name() + ":" + template.getScene().name() + ":" + action + ":" + factor;

        return result;
    }

}
