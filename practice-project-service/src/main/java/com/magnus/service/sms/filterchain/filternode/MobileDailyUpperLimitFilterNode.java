package com.magnus.service.sms.filterchain.filternode;

import com.magnus.infrastructure.utils.FatigueUtils;
import com.magnus.service.sms.SmsRedisKeyFactory;
import com.magnus.service.sms.enums.SmsTemplateEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 手机号 每日发送上限校验，也即针对手机号的疲劳校验
 * <p>
 * 凌晨后次数重置
 *
 * @author gaosong
 */
@Component
@Slf4j
public class MobileDailyUpperLimitFilterNode {

    @Resource
    private FatigueUtils fatigueUtils;

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    public Boolean checkMobileDailyUpperLimit(String mobile, SmsTemplateEnum smsTemplate) {
        log.info("[MobileDailyUpperLimitFilterNode checkMobileDailyUpperLimit] step in");

        String redisKey = SmsRedisKeyFactory.buildMobileDailyUpperLimitKey(smsTemplate, mobile);
        log.info("[MobileDailyUpperLimitFilterNode checkMobileDailyUpperLimit] redisKey:{}", redisKey);

        int upperTimes = smsTemplate.getMobileLimitTimes();

        return this.fatigueCheck(upperTimes, redisKey);

    }

    public Boolean fatigueCheck(int upperTimes, String key) {
        return fatigueUtils.fatigueCheck(upperTimes, key);
    }


}
