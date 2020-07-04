package com.imooc.security.browser.config;

import com.imooc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SecurityProperties securityProperties;

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
        http.formLogin()
                ///authentication/require  采用接口的方式来可配置化我们的登录
                .loginPage("/authentication/require")
                //使用 UserNamePasswordAuthenticationFilter  来处理这个页面请求提交的用户信息
                .loginProcessingUrl("/authentication/form")
                //设置页面的授权方式
                .and()
                .authorizeRequests()
                //不需权限认证
                .antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                //去掉跨站防护
                .and()
                .csrf().disable();
    }
}
