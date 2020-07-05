package com.imooc.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhang.suxing
 * @date 2020/7/2 23:06
 **/
@ConfigurationProperties(prefix = "imooc.security")
@Data
public class SecurityProperties {
    /**
     * 浏览器相关配置
     */
    private BrowserProperties browser = new BrowserProperties();
    /**
     * 验证码相关配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();
}
