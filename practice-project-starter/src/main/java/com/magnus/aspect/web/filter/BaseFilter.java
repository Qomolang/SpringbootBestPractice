package com.magnus.aspect.web.filter;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * filter本身不提供路径加白，只能使用模式匹配，如果想加白只能手动放行
 *
 * filter不受Spring管理，无法通过Resource注解引入Spring Bean，如果需要请在init方法中通过反射引入
 *
 * 使用@WebFilter注解，则无需在配置中手动注册Filter，并可通过@Order控制filter执行顺序，但需要在启动类上加@ServletComponentScan
 * @author 84028
 */
@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class BaseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        //通过反射找到bean
        //ctx.getBean();
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
