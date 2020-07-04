package com.imooc.security.browser.support;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhang.suxing
 * @date 2020/7/4 10:05
 **/
@Data
@AllArgsConstructor
public class SimpleResponse {
    /**
     * 认证页面返回
     */
    private Object content;
}
