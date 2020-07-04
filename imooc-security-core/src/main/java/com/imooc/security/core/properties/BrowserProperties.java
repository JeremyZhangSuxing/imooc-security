package com.imooc.security.core.properties;

import com.imooc.security.core.LoginType;
import lombok.Data;

/**
 * @author zhang.suxing
 * @date 2020/7/2 23:07
 **/
@Data
public class BrowserProperties {

    private String loginPage = "/imooc-sign.html";

    private LoginType loginType = LoginType.JSON;
}
