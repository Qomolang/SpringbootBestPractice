package com.magnus.service.sms;

import com.magnus.infrastructure.utils.DateOps;
import com.magnus.infrastructure.utils.FatigueUtils;
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
import java.util.function.Supplier;

/**
 * @author gaosong
 */
@Service
@Slf4j
public class SmsDomainService {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Resource
    private FatigueUtils fatigueUtils;

    /**
     * 仅发送短信
     * 发送前校验不放在domain层，因不同的业务逻辑会有不同的校验逻辑
     */
    public void sendSms(SmsTemplateEnum smsTemplate, String subject) {

        //生成六位数随机验证码
        String smsCode = RandomUtils.getRandomNumeric(6);

        SmsDTO smsInfo = SmsDTO.builder()
                .smsCode(smsCode)
                .nextSendLimitTime(DateUtils.addSeconds(new Date(), smsTemplate.getSendIntervalSeconds()))
                .build();

        //缓存中保留一份
        String sendSmsRedisKey = SmsRedisKeyFactory.buildSendSmsLimitKey(smsTemplate, subject);
        redisTemplate.opsForValue().set(sendSmsRedisKey, smsInfo, smsTemplate.getValidDurationMinutes(), TimeUnit.MINUTES);

        //发送给用户一份
        log.info("验证码发送成功，验证码为:{}", smsCode);

        //增加手机号日疲劳次数
        String mobileDailyUpperLimitKeyobileDailyUpperLimitKey = SmsRedisKeyFactory.buildMobileDailyUpperLimitKey(smsTemplate, subject);
        Long timeInterval = DateOps.getNextDayIntervalInMilliseconds();
        fatigueUtils.incrementFatigueTimes(timeInterval, TimeUnit.MILLISECONDS, mobileDailyUpperLimitKeyobileDailyUpperLimitKey);
    }

    public String validateSmsCode(String smsCode, SmsTemplateEnum smsTemplate, String subject) {
        if (StringUtils.isBlank(smsCode)) {
            throw new RuntimeException("验证码不能为空");
        }

        String sendSmsRedisKey = SmsRedisKeyFactory.buildSendSmsLimitKey(smsTemplate, subject);

        SmsDTO smsDTO = (SmsDTO) redisTemplate.opsForValue().get(sendSmsRedisKey);
        if (smsDTO == null) {
            throw new RuntimeException("验证码已过期，请重新发送");
        }

        String smsCodeInCache = smsDTO.getSmsCode();
        if (!StringUtils.equals(smsCodeInCache, smsCode)) {
            throw new RuntimeException("验证码错误，请重新发送");
        }

        return sendSmsRedisKey;
    }

    public void validateSmsCodeAndExpire(String smsCode, SmsTemplateEnum smsTemplate, String subject, Supplier remoteInterface) {

        String sendSmsRedisKey = this.validateSmsCode(smsCode, smsTemplate, subject);

        if (remoteInterface != null) {
            remoteInterface.get();
        }

        //验证码只能使用一次，使用后使缓存失效
        redisTemplate.expire(sendSmsRedisKey, 0, TimeUnit.SECONDS);

    }

}
