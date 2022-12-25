package com.magnus.service.sms.filternode;

import com.magnus.service.sms.SmsRedisKeyFactory;
import com.magnus.service.sms.enums.SmsTemplateEnum;
import com.magnus.service.sms.model.SmsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;

/**
 * 校验发送间隔
 *
 * @author gaosong
 */
@Component
@Slf4j
public class SendIntervalFilterNode {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 校验主体应只有两种 userId 和 mobile。当且仅当userId达到百亿级别，才会和mobile冲突，因此此处不考虑再做区分
     */
    public boolean checkSendInterval(String subject, SmsTemplateEnum smsTemplate) {
        log.info("[SendIntervalFilterNode checkSendInterval] step in");

        String sendSmsRedisKey = SmsRedisKeyFactory.buildSendSmsLimitKey(smsTemplate, subject);

        log.info("[SendIntervalFilterNode checkSendInterval] sendSmsRedisKey:{}", sendSmsRedisKey);

        SmsDTO smsDTO = (SmsDTO) redisTemplate.opsForValue().get(sendSmsRedisKey);
        Date nextSendLimitTime = smsDTO.getNextSendLimitTime();

        Date now = new Date();
        if (now.before(nextSendLimitTime)) {
            throw new RuntimeException("发送过快，请在上次发送后等待一分钟");
        }

        return true;
    }
}
