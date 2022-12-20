package com.magnus.service.sms.filternode;

import com.magnus.service.sms.SmsRedisKeyFactory;
import com.magnus.service.sms.enums.SmsTemplateEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
    private RedisTemplate<String, Serializable> redisTemplate;

    public Boolean checkMobileDailyUpperLimit(String mobile, SmsTemplateEnum smsTemplate) {
        log.info("[MobileDailyUpperLimitFilterNode checkMobileDailyUpperLimit] step in");

        String redisKey = SmsRedisKeyFactory.buildMobileDailyUpperLimitKey(smsTemplate, mobile);
        log.info("[MobileDailyUpperLimitFilterNode checkMobileDailyUpperLimit] redisKey:{}", redisKey);

        int upperTimes = smsTemplate.getMobileLimitTimes();

        return this.isFatigue(upperTimes, redisKey);

    }

    public Boolean isFatigue(int upperTimes, String key) {
        Integer currentVersion = (Integer) redisTemplate.opsForValue().get(key);

        //疲劳情况判断
        if (currentVersion != null && currentVersion >= upperTimes) {
            throw new RuntimeException("该手机号今日发送次数达到上限，请明天再进行发送");
        }

        Long newVersion = redisTemplate.opsForValue().increment(key);
        //increment方法不会添加过期时间，需要额外调用expire方法添加过期时间
        if (newVersion == 1) {
            Long timeInterval = this.getNextDayIntervalInMilliseconds();
            redisTemplate.expire(key, timeInterval, TimeUnit.MILLISECONDS);
        }

        return false;
    }

    private Long getNextDayIntervalInMilliseconds() {
        Date now = new Date();

        //算到下一天 2022/02/02 22:22 -> 2022/02/03 00:00
        Date nextDayMidnight = DateUtils.ceiling(now, Calendar.DATE);

        Long interval = nextDayMidnight.getTime() - now.getTime();
        return interval;
    }


}
