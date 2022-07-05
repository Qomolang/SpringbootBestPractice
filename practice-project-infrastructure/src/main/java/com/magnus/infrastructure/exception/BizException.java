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

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "BIZ_ERROR";

    public BizException(String errCode, String errMessage) {
        super(errMessage);
        errorCode = errCode;
    }

}