package com.magnus.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    /**
     * 样例错误码
     */
    BASE("000000","错误测试"),
    ;

    private String errorCode;

    private String errorMsg;

}
