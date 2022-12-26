package com.magnus.service.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaosong
 */
@Getter
@AllArgsConstructor
public enum SmsTemplateEnum {

    /**
     * 考试平台
     */
    EXAM_PLATFORM(SmsBizEnum.EXAM_PLATFORM, SmsSceneEnum.LOG_IN, 60 ,20,5),

    /**
     * 数字知识平台
     */
    DATA_KNOWLEDGE_PLATFORM(SmsBizEnum.DATA_KNOWLEDGE_PLATFORM, SmsSceneEnum.LOG_IN, 60 ,20,5),

    ;

    /**
     * 业务
     */
    private final SmsBizEnum biz;
    /**
     * 场景
     */
    private final SmsSceneEnum scene;
    /**
     * 发送间隔（秒）
     */
    private final int sendIntervalSeconds;

    /**
     * 手机号每日发送上限
     */
    private final int mobileLimitTimes;

    /**
     * 有效时间（分钟）
     */
    private final int validDurationMinutes;

}
