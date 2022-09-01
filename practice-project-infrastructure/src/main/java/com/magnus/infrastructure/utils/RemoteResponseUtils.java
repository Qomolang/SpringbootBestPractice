package com.magnus.infrastructure.utils;

import com.alibaba.cola.dto.SingleResponse;
import com.magnus.infrastructure.exception.BizException;
import com.magnus.infrastructure.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;


import java.util.function.Supplier;

/**
 * @author gaosong
 */
@Slf4j
public class RemoteResponseUtils {

    public static <T, R> R dealResponse(Supplier<SingleResponse<R>> remoteInterface, T request, ErrorCodeEnum errorCodeEnum) {
        SingleResponse<R> response;

        try {
            response = remoteInterface.get();
        } catch (Exception e) {
            log.error("rpc invoke  exception, request:{}, e:{}", request, e);
            throw new BizException(errorCodeEnum);
        }

        if (response == null || !response.isSuccess()) {
            log.error("rpc invoke fail, request:{}, response:{}", request, response);
            throw new BizException(errorCodeEnum);
        }
        return response.getData();
    }

}
