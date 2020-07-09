package com.imooc.security.core.constants;

/**
 * @author zhang.suxing
 * @date 2020/7/9 23:06
 **/
public interface SecurityConstants {
    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    String GENERATOR_SUFFIX  = "CodeGenerator";

    String PROCESSOR_SUFFIX = "CodeProcessor";

    /**
     * 验证码放入session的key
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_IMAGE_CODE";
}
