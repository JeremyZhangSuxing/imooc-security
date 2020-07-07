package com.imooc.security.core.properties;

import lombok.Data;

/**
 * @author zhang.suxing
 * @date 2020/7/5 17:25
 **/
@Data
public class ImageCodeProperties extends SmsCodeProperties {

    private int width = 67;

    private int height = 23;

    private String url;
}
