package com.magnus.service.sms;

import com.google.common.base.Preconditions;
import com.magnus.service.sms.enums.SmsTemplateEnum;
import com.magnus.service.sms.filternode.MobileDailyUpperLimitFilterNode;
import com.magnus.service.sms.filternode.SendIntervalFilterNode;
import com.magnus.service.sms.model.CommonBizContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gaosong
 */
@Component
public class CommonBizFilterChain {

    @Resource
    private SendIntervalFilterNode sendIntervalFilterNode;
    @Resource
    private MobileDailyUpperLimitFilterNode mobileDailyUpperLimitFilterNode;

    /**
     * true代表通过
     */
    public boolean doCheck(CommonBizContext context) {
        Preconditions.checkNotNull(context);

        String mobile = context.getMobile();
        SmsTemplateEnum smsTemplate = context.getSmsTemplate();

        boolean sendIntervalFlag = sendIntervalFilterNode.checkSendInterval(mobile, smsTemplate);
        if (!sendIntervalFlag) {
            throw new RuntimeException("发送过快，请在上次发送后等待一分钟");
        }

        boolean mobileDailyUpperLimitFlag = mobileDailyUpperLimitFilterNode.checkMobileDailyUpperLimit(mobile, smsTemplate);
        if (!mobileDailyUpperLimitFlag) {
            throw new RuntimeException("该手机号今日发送次数达到上限，请明天再进行发送");
        }

        return true;
    }
}
