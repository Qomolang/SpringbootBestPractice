package com.magnus.service.sms;

import com.magnus.service.sms.enums.SmsTemplateEnum;
import com.magnus.service.sms.model.ExampleBizContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gaosong
 */
@Service
public class SmsBizService {

    @Resource
    private ExampleBizFilterChain exampleBizFilterChain;
    @Resource
    private SmsDomainService smsDomainService;

    public void sendExamPlatformLoginSms(String mobile) {

        SmsTemplateEnum smsTemplateEnum = SmsTemplateEnum.EXAM_PLATFORM;

        exampleBizFilterChain.doCheck(ExampleBizContext.builder()
                .smsTemplate(smsTemplateEnum)
                .mobile(mobile)
                .build());

        smsDomainService.sendSms(SmsTemplateEnum.EXAM_PLATFORM, mobile);
    }

}
