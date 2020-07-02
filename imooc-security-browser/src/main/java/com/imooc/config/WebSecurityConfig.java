package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zhang.suxing
 * @date 2020/7/1 22:04
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入密码加密组件
     *
     * @return md5加密接口
     */
    @Bean
    public PasswordEncoder passwordEncoern() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 自己赋予http认证方式
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO
        super.configure(http);
    }
}
