package com.magnus.infrastructure.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class FatigueUtils {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 校验并增加疲劳次数
     * 该方法仅适用于只有疲劳校验一种校验的情况
     * 如果有多种校验请勿使用该方法，因为这会使得疲劳校验的地位高于其他校验方法，此时请使用责任链模式
     */
    public Boolean fatigueCheckAndIncrementFatigueTimes(int limitTimes, String key, long timeout, TimeUnit timeUnit, Supplier supplier, String exceptionMsg) {

        boolean fatigueFlag = this.fatigueCheck(limitTimes, key);
        if (!fatigueFlag) {
            //如果没通过校验，则抛出异常
            throw new RuntimeException(exceptionMsg);
        }

        //执行方法
        if (supplier != null) {
            supplier.get();
        }

        //增加疲劳次数
        this.incrementFatigueTimes(timeout, timeUnit, key);

        return true;
    }


    /**
     * true代表通过疲劳校验 false代表未通过疲劳校验
     */
    public boolean fatigueCheck(int limitTimes, String key) {
        Integer currentVersion = (Integer) redisTemplate.opsForValue().get(key);

        //疲劳情况判断
        if (currentVersion != null && currentVersion >= limitTimes) {
            return false;
        }

        return true;
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
