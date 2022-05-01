package com.magnus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.annotation.Resource;

/**
 * 拦截器注册中心
 * @author 84028
 */
@Configuration
public class InterceptorsRegistry implements WebMvcConfigurer {
//    @Resource
//    LocaleChangeInterceptor i18nInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册I18n拦截器
//        registry.addInterceptor(i18nInterceptor);
    }
}