package com.magnus.infrastructure.exception;

import lombok.Getter;

/**
 * BizException is known Exception, no need retry
 *
 * @author Frank Zhang
 */
@Getter
public class BizException extends RuntimeException {

    private String errorCode;

    private String errorMsg;

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "BIZ_ERROR";

    public BizException(String errCode, String errMessage) {
        super(errMessage);
        this.errorCode = errCode;
        this.errorMsg = errMessage;
    }

    public BizException(ErrorCodeEnum errCode) {
        super(errCode.getErrorMsg());
        this.errorCode = errCode.getErrorCode();
        this.errorMsg = errCode.getErrorMsg();

    }

}