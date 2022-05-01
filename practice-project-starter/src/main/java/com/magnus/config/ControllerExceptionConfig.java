package com.magnus.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;

/**
 * @author 84028
 */
@RestControllerAdvice
public class ControllerExceptionConfig {

    /**
     * controller层异常兜底
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public String handleExceptions(RuntimeException e, WebRequest webRequest, HandlerMethod handlerMethod){

        //(0) 可做选择性处理 分考虑到的异常（本应用应用异常及转化为本应用异常的CE RE）和未考虑到的异常
        //if(e instanceof ){}

        //(1) 打印调用路径

        //(2) 打印出问题入参 最好是两个 一个是controller层入参 一个是触发异常函数的入参

        //todo 真实使用时，转化为应用的ServiceResult
        return e.getMessage();
    }

}
