package com.magnus.service.sms.model;

import com.magnus.service.sms.enums.SmsTemplateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaosong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExampleBizContext {

    private String mobile;

    private SmsTemplateEnum smsTemplate;

}
