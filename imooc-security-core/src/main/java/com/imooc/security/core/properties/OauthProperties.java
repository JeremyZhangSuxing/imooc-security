package com.imooc.security.core.properties;

import lombok.Data;

/**
 * @author zhang.suxing
 * @date 2020/9/6 13:50
 **/
@Data
public class OauthProperties {
    private OauthClientProperties [] oauthClientProperties  = {};
}
