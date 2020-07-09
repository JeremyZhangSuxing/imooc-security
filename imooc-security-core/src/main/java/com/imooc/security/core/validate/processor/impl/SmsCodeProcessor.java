package com.imooc.security.core.validate.processor.impl;

import com.imooc.security.core.validate.dto.ValidateCode;
import com.imooc.security.core.validate.suppot.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author zhang.suxing
 * @date 2020/7/7 21:38
 **/
@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateProcessor<ValidateCode> {
    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 发送验证码方法
     *
     * @param request      请求
     * @param validateCode 验正码类型
     * @throws Exception  exception
     */
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.sendMsg(mobile, validateCode.getCode());
    }
}
