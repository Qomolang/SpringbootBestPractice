package com.magnus.infrastructure.exception.catchlog;


import lombok.extern.slf4j.Slf4j;

/**
 * ResponseHandler
 *
 * @author Frank Zhang
 * @date 2020-11-10 3:18 PM
 */
@Slf4j
public class ResponseHandler {

    public static Object handle(Class returnType, String errCode, String errMsg) {
        if (isColaResponse(returnType)) {
            return handleColaResponse(returnType, errCode, errMsg);
        }
        return null;
    }


    private static Object handleColaResponse(Class returnType, String errCode, String errMsg) {
        //todo 真实业务中包装成自己的response
        return null;
//        try {
//            Response response = (Response)returnType.newInstance();
//            response.setSuccess(false);
//            response.setErrCode(errCode);
//            response.setErrMessage(errMsg);
//            return response;
//        }
//        catch (Exception ex){
//            log.error(ex.getMessage(), ex);
//            return  null;
//        }
    }

    private static boolean isColaResponse(Class returnType) {
        //todo 真实业务语义中检测是否是自己的response
        //        return  returnType == Response.class || returnType.getGenericSuperclass() == Response.class;
        return true;
    }
}
