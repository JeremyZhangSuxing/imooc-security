package com.imooc.security.core.config;

import com.imooc.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhang.suxing
 * @date 2020/7/2 22:56
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
    //此配置类的作用是使 SecurityProperties 中的相关配置生效
}
