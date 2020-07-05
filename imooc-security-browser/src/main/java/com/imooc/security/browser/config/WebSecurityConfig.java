package com.imooc.security.browser.config;

import com.imooc.security.browser.authentication.ImoocAuthenticationFailureHandler;
import com.imooc.security.browser.authentication.ImoocAuthenticationSuccessHandler;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.filter.ValidateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;

/**
 * @author zhang.suxing
 * @date 2020/7/1 22:04
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ImoocAuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Bean
    public ValidateFilter validateFilter() throws ServletException {
        ValidateFilter validateFilter = new ValidateFilter();
        validateFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        validateFilter.setSecurityProperties(securityProperties);
//        validateFilter.afterPropertiesSet();
        return validateFilter;
    }


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
        //在密码验证之前 先validate验证码
        http.addFilterBefore(validateFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                ///authentication/require  采用接口的方式来可配置化我们的登录
                .loginPage("/authentication/require")
                //使用 UserNamePasswordAuthenticationFilter  来处理这个页面请求提交的用户信息
                .loginProcessingUrl("/authentication/form")
                //login 成功处理方式
                .successHandler(imoocAuthenticationSuccessHandler)
                //login 失败的处理方式
                .failureHandler(imoocAuthenticationFailureHandler)
                //设置页面的授权方式
                .and()
                .authorizeRequests()
                //不需权限认证
                .antMatchers("/authentication/require",
                        securityProperties.getBrowser().getLoginPage(),
                        "/error",
                        "/code/image").permitAll()
                .anyRequest()
                .authenticated()
                //去掉跨站防护
                .and()
                .csrf().disable();
    }
}
