package com.imooc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author zhang.suxing
 * @date 2020/7/1 21:43
 **/
@Component
@Slf4j
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //1.根据自己需要从db查询用户信息
        log.info("======MyUserDetailService, userName {}", userName);
        String keys = "123456";
        //2.校验逻辑自己实现 设置构造函数的参数值
        //3.用户的密码是加密的 db中的是encode后的密码  多次加密会生成不同的盐 密码会不一样 但是反解析后结果相同，安全性大大提高
        String encode = passwordEncoder.encode(keys);
        log.info("======MyUserDetailService loadUserByUsername encode passWord {}", encode);
        return new User(userName, keys, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
