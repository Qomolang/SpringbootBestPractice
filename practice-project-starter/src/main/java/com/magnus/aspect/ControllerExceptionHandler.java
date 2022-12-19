package com.magnus.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * controller层方法的统一异常处理
 *
 * @author gaosong
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 参数校验异常兜底
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String exceptionHandler(MethodArgumentNotValidException e) {
        //todo 参数值到哪里找？
        String clazzName = e.getParameter().getMember().getDeclaringClass().getSimpleName();
        String methodName = e.getParameter().getMethod().getName();

        //        e.getParameter().getDeclaringClass()
//        log.error();
        String errorMsg = e.getBindingResult().getAllErrors().stream()
                .map(foo -> foo.getDefaultMessage())
                .collect(Collectors.joining(";"));
        return errorMsg;
    }

    /**
     * controller层异常兜底
     */
    @ExceptionHandler(value = Exception.class)
    public String commonExceptionHandler(RuntimeException e, HttpServletRequest request, HandlerMethod handlerMethod) {
        log.info("[ControllerExceptionConfig handleExceptions]");
        String Url = request.getRequestURL().toString();
        String clazzName = handlerMethod.getMethod().getDeclaringClass().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        LocalDateTime time = LocalDateTime.now();


        //(0) 可做选择性处理 分考虑到的异常（本应用应用异常及转化为本应用异常的CE RE）和未考虑到的异常
        //if(e instanceof ){}

        //(1) 打印调用路径

        //(2) 打印出问题入参 最好是两个 一个是controller层入参 一个是触发异常函数的入参

        //todo 真实使用时，转化为应用的ServiceResult
        return e.getMessage();
    }

}
