package com.imooc.config;

import com.imooc.filter.TimeFilter;
import com.imooc.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * @author zhang.suxing
 * @date 2020/6/26 15:12
 **/
@Configuration
public class DemoWebConfig implements WebMvcConfigurer {
    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     * 将第三方的filter引如spring过滤期器
     * 其他场景如要引入第三方插件 也可以使用bean的方式配置注入
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<TimeFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        TimeFilter timeFilter = new TimeFilter();
        filterRegistrationBean.setFilter(timeFilter);
        //设置可过滤的url 不干扰其他测试设置为 /user
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/user"));
        return filterRegistrationBean;
    }

    /**
     * 增加自定义的interceptor到 InterceptorRegistry 注册组件去
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations and resource handler requests.
     * Interceptors can be registered to apply to all requests or be limited
     * to a subset of URL patterns.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(timeInterceptor);  不影响后续测试 先注释
    }
}
