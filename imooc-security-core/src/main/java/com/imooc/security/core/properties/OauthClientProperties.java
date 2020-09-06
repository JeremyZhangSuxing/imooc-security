package com.imooc.security.core.properties;

import lombok.Data;

/**
 * @author zhang.suxing
 * @date 2020/9/6 13:42
 **/
@Data
public class OauthClientProperties {
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 客户端密码
     */
    private String clientSecurity;
    /**
     * 默认3600过期
     */
    private int expirePeriod = 3600;

}
