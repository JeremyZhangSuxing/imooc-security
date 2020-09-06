package com.imooc.security.core.social.qq.api.impl;

import com.imooc.security.core.social.qq.api.QQ;
import com.imooc.security.core.social.qq.api.entity.QQUserInfo;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @author zhang.suxing
 * @date 2020/8/11 22:01
 **/
public class QQimpl extends AbstractOAuth1ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "GET OPEN ID URI";

    private static final String URL_GET = "GET QQ URI";

    private String appId;

    private String openId;

    public QQimpl(String appId, String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER.name(),"","");
    }

    @Override
    public QQUserInfo getUserInfo() {
        return null;
    }
}
