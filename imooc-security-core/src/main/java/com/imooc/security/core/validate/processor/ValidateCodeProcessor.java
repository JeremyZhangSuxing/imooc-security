package com.imooc.security.core.validate.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author zhang.suxing
 * @date 2020/7/7 21:33
 **/
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session的key
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_IMAGE_CODE";

    /**
     * 创建校验码
     * @param request 存放request response 信息
     * @throws Exception
     */
    void create(ServletWebRequest request)throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);
}
