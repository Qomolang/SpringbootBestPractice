package com.magnus.service.sms;

import com.alibaba.cola.dto.SingleResponse;
import com.magnus.infrastructure.utils.RandomUtils;
import com.magnus.service.sms.enums.SmsTemplateEnum;
import com.magnus.service.sms.model.SmsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author gaosong
 */
@Service
@Slf4j
public class SmsService {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    public void sendSms(SmsTemplateEnum smsTemplate, String subject) {

        //生成六位数随机验证码
        String smsCode = RandomUtils.getRandomNumeric(6);

        SmsDTO smsInfo = SmsDTO.builder()
                .smsCode(smsCode)
                .nextSendLimitTime(DateUtils.addSeconds(new Date(), smsTemplate.getSendIntervalSeconds()))
                .build();

        //缓存中保留一份
        String sendSmsRedisKey = SmsRedisKeyFactory.buildSendSmsLimitKey(smsTemplate, subject);
        redisTemplate.opsForValue().set(sendSmsRedisKey, smsInfo);

        //发送给用户一份
        log.info("验证码发送成功，验证码为:{}", smsCode);
    }

    public void validateSmsCodeAndExpire(String smsCode, SmsTemplateEnum smsTemplate, String subject, Supplier remoteInterface) {

        String sendSmsRedisKey = SmsRedisKeyFactory.buildSendSmsLimitKey(smsTemplate, subject);

        SmsDTO smsDTO = (SmsDTO) redisTemplate.opsForValue().get(sendSmsRedisKey);
        String smsCodeInCache = smsDTO.getSmsCode();
        if (!StringUtils.equals(smsCodeInCache, smsCode)) {
            throw new RuntimeException("验证码错误，请重新发送");
        }

        remoteInterface.get();

        //验证码只能使用一次，使用后使缓存失效
        redisTemplate.expire(sendSmsRedisKey, 0, TimeUnit.SECONDS);

    }

}
