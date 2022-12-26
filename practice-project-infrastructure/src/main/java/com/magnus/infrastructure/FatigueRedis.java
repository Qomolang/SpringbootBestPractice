package com.magnus.infrastructure;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class FatigueRedis {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * true代表疲劳，false代表未疲劳
     */
    public Boolean isFatigue(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key));

        //todo 下面三个参数需要修改
        int limitTimes = 3;
        long timeout = 30;
        TimeUnit timeUnit = TimeUnit.SECONDS;

        Boolean isFatigue = isFatigue(limitTimes, key);
        incrementFatigueTimes(timeout, timeUnit, key);

        return isFatigue;
    }

    /**
     * 校验是否疲劳
     */
    public Boolean isFatigue(int limitTimes, String key) {
        Integer currentVersion = (Integer) redisTemplate.opsForValue().get(key);

        //疲劳情况判断
        if (currentVersion != null && currentVersion >= limitTimes) {
            //todo 考虑抛异常
            return true;
        }

        return false;
    }

    /**
     * 增加疲劳次数
     */
    public void incrementFatigueTimes(long timeout, TimeUnit timeUnit, String key) {
        Long newVersion = redisTemplate.opsForValue().increment(key);
        //increment方法不会添加过期时间，需要额外调用expire方法添加过期时间
        if (newVersion == 1) {
            redisTemplate.expire(key, timeout, timeUnit);
        }
    }

}
