package com.imooc.security.core.config;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.suppot.ValidateCodeGenerator;
import com.imooc.security.core.validate.suppot.impl.ImageCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhang.suxing
 * @date 2020/7/5 21:19
 **/
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 注入的优点 会去去容器中找这个bean 重构代码时不去删除旧的代码 而是覆盖之前的bean
     * 增量的方式去改变之前的逻辑 不去变更他人的代码
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }
}
