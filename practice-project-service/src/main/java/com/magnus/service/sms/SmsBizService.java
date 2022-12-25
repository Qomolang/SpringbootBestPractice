package com.magnus.service.sms;

import com.magnus.service.sms.enums.SmsTemplateEnum;
import com.magnus.service.sms.model.CommonBizContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gaosong
 */
@Service
public class SmsBizService {

    @Resource
    private CommonBizFilterChain commonBizFilterChain;
    @Resource
    private SmsDomainService smsDomainService;

    public void sendExamPlatformLoginSms(String mobile) {

        SmsTemplateEnum smsTemplateEnum = SmsTemplateEnum.EXAM_PLATFORM;

        commonBizFilterChain.doCheck(CommonBizContext.builder()
                .smsTemplate(smsTemplateEnum)
                .mobile(mobile)
                .build());

        smsDomainService.sendSms(SmsTemplateEnum.EXAM_PLATFORM, mobile);
    }

}
