package com.magnus.service.sms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 发送验证码前，应在缓存中保存一份信息，此即为信息结构体
 * @author gaosong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsDTO implements Serializable {

    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     * 超过该值后，才允许进行下一次发送
     */
    private Date nextSendLimitTime;

}
