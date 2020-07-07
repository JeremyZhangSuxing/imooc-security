package com.imooc.security.core.validate.suppot.impl;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.dto.ValidateCode;
import com.imooc.security.core.validate.suppot.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;

/**
 * @author zhang.suxing
 * @date 2020/7/5 21:10
 **/
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 生成图片验证码
     **/
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        return ValidateCode.builder()
                .code(RandomStringUtils.randomNumeric(securityProperties.getCode().getMsg().getLength()))
                .expireTime(LocalDateTime.now().plusSeconds(securityProperties.getCode().getMsg().getExpireTime()))
                .build();
    }

}
