package com.magnus.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

@Aspect
@Slf4j
@Component
public class CommonExceptionHandler {

    //todo 换一种以类为粒度的 更不容易出错的切面指定
    @Pointcut("execution(* com.magnus.service.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object invoke(ProceedingJoinPoint invocation) throws Throwable {
        Object result = null;
        try {
            result = invocation.proceed();
        } catch (ConstraintViolationException e) {
            //todo 完善报错信息 一行之内打印 1.错误的类型（参数校验错误） 2.方法 3.错误具体原因（哪个值空了） 4.导致错误的参数 5.错误堆栈

            Signature signature = invocation.getSignature();
            for (Object arg : invocation.getArgs()) {
                log.error("parameter check error, args:{}", arg.toString());
            }
            log.error("parameter check error, class and method:{}", signature.toShortString());

            log.error("parameter check error,detailMessage:{}", e.getMessage());

        }

        return result;
    }

}
