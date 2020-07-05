package com.imooc.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author zhang.suxing
 * @date 2020/6/26 14:57WebConfig
 * <p>
 * filter 不知道request 是哪个controller发起的 @
 * 本地的可直接注解引入
 * 第三方的filter 怎么加入 spring 的过滤器链
 **/
@Slf4j
//@Component
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("======TimeFilter is init");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("======TimeFilter doFilter start");
        long start = System.currentTimeMillis();
        //处理请求的逻辑
        filterChain.doFilter(servletRequest, servletResponse);
        long end = System.currentTimeMillis();
        log.info("======TimeFilter doFilter end spend time =" + (end - start) + "ms");

    }

    @Override
    public void destroy() {
        log.info("======TimeFilter is destroy");

    }
}
