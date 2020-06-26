package com.imooc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zhang.suxing
 * @date 2020/6/21 17:08
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //去掉防止跨站伪造请求的配置
        http.csrf().disable();
        //配置不需要登录验证

        http.authorizeRequests()
                .anyRequest().permitAll().and().logout().permitAll()
        ;
    }
}
