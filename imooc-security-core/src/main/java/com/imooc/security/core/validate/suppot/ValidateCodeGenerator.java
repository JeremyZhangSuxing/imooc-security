package com.imooc.security.core.validate.suppot;

import com.imooc.security.core.validate.dto.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author zhang.suxing
 * @date 2020/7/5 21:08
 **/
public interface ValidateCodeGenerator {

    /**
     * 图片验证码
     * @param request  sdaf
     * @return
     */
    ImageCode generate(ServletWebRequest request);
}
