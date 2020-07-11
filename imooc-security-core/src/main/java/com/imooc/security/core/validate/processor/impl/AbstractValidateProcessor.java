package com.imooc.security.core.validate.processor.impl;

import com.imooc.security.core.constants.SecurityConstants;
import com.imooc.security.core.exception.ValidateException;
import com.imooc.security.core.validate.dto.ValidateCode;
import com.imooc.security.core.validate.enums.ValidateCodeType;
import com.imooc.security.core.validate.processor.ValidateCodeProcessor;
import com.imooc.security.core.validate.suppot.ValidateCodeGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author zhang.suxing
 * @date 2020/7/7 21:37
 **/
public abstract class AbstractValidateProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
    /**
     * 操作session 的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集系统中所有的{@link ValidateCodeGenerator}的实现
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 创建校验码
     *
     * @param request 存放request response 信息
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成验证码
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getProcessType(request);
        ValidateCodeGenerator generator = validateCodeGenerators.get(type + SecurityConstants.GENERATOR_SUFFIX);
        return (C) generator.generate(request);
    }

    /**
     * 保存code信息到session
     */
    private void save(ServletWebRequest request, C validateCode) {
        //request 信息放入session中，设置验证码属性
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX, validateCode.getCode());
    }

    /**
     * 发送验证码方法
     *
     * @param request      请求
     * @param validateCode 验正码类型
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 获取校验码类型
     */
    private String getProcessType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

    /**
     * 通用校验验证码逻辑
     *
     * @param request 请求信息
     * @throws Exception
     */
    @Override
    public void validate(ServletWebRequest request){
        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

    /**
     * 构建验证码放入session时的key
     *
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = getProcessType(request);
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
}
