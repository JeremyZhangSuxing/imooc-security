package com.imooc.security.core.validate.suppot;

/**
 * @author zhang.suxing
 * @date 2020/7/7 20:40
 **/
public interface SmsCodeSender {
    /**
     * send 手机号验证码
     * @param mobile phone
     * @param code validate code
     */
    void sendMsg(String mobile,String code);
}
