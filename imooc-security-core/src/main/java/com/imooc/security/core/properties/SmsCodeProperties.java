package com.imooc.security.core.properties;

import lombok.Data;

/**
 * @author zhang.suxing
 * @date 2020/7/7 21:04
 **/
@Data
public class SmsCodeProperties {
    private int expireTime = 60;

    private int length = 4;
}
