package com.imooc.security.core.validate.processor.impl;

import com.imooc.security.core.validate.dto.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @author zhang.suxing
 * @date 2020/7/7 21:38
 **/
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateProcessor<ImageCode> {


    /**
     * 发送验证码方法
     *
     * @param request      请求
     * @param validateCode 验正码类型
     * @throws Exception exception
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());

    }
}
