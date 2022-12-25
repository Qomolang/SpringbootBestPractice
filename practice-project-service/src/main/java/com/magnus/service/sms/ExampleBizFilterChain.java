package com.magnus.service.sms;

import com.google.common.base.Preconditions;
import com.magnus.service.sms.enums.SmsTemplateEnum;
import com.magnus.service.sms.filternode.MobileDailyUpperLimitFilterNode;
import com.magnus.service.sms.filternode.SendIntervalFilterNode;
import com.magnus.service.sms.model.ExampleBizContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gaosong
 */
@Component
public class ExampleBizFilterChain {

    @Resource
    private SendIntervalFilterNode sendIntervalFilterNode;
    @Resource
    private MobileDailyUpperLimitFilterNode mobileDailyUpperLimitFilterNode;

    /**
     * true代表通过
     */
    public boolean doCheck(ExampleBizContext context) {
        Preconditions.checkNotNull(context);

        String mobile = context.getMobile();
        SmsTemplateEnum smsTemplate = context.getSmsTemplate();

        boolean sendIntervalFlag = sendIntervalFilterNode.checkSendInterval(mobile, smsTemplate);
        if (!sendIntervalFlag) {
            return false;
        }

        return true;
    }
}
