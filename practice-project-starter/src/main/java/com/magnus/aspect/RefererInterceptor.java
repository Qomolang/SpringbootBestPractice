package com.magnus.aspect;

import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RefererInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String referer = request.getHeader(HttpHeaders.REFERER);
        log.info("RefererInterceptor referer:{}", referer);
        if (StringUtils.isNoneBlank(referer)) {
            //todo 测试是否与线上域名开头域名一致 不一致则抛出异常
        }
        return true;
    }

}
