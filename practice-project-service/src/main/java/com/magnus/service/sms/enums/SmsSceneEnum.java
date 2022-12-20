package com.magnus.service.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gaosong
 */
@Getter
@AllArgsConstructor
public enum SmsSceneEnum {

    /**
     * 登录
     */
    LOG_IN,

    /**
     * 修改密码
     */
    MODIFIED_SECRET,

    /**
     * 通用场景，即无需区分用途的场景
     *
     * 注意，通用场景的验证码并不能通过其他场景的校验。如有这种需求请在业务层定制
     */
    SHARED,
    ;

}
