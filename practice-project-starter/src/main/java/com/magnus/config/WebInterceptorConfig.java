package com.magnus.config;

import com.magnus.aspect.RefererInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 拦截器注册中心
 *
 * @author 84028
 */
@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {
    //@Resource
    //LocaleChangeInterceptor i18nInterceptor;
    @Resource
    RefererInterceptor refererInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(refererInterceptor)
                //拦截所有路径
                .addPathPatterns("/**")
                .order(1)
                //加白路径
                .excludePathPatterns(new ArrayList<>());
        //注册I18n拦截器
//        registry.addInterceptor(i18nInterceptor);
    }
}