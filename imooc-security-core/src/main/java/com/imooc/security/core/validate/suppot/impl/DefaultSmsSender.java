package com.imooc.security.core.validate.suppot.impl;

import com.imooc.security.core.validate.suppot.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhang.suxing
 * @date 2020/7/7 20:46
 **/
@Component
@Slf4j
public class DefaultSmsSender implements SmsCodeSender {
    /**
     * send 手机号验证码
     *
     * @param mobile phone
     * @param code   validate code
     */
    @Override
    public void sendMsg(String mobile, String code) {
        log.info("====== DefaultSmsSender sendMsg mobile {}, code {}", mobile, code);
    }
}
