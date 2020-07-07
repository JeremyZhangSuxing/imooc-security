package com.imooc.security.browser.controller;

import com.imooc.security.browser.support.SimpleResponse;
import com.imooc.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zhang.suxing
 * @date 2020/7/2 23:14
 **/
@RestController
@Slf4j
public class BrowserSecurityController {
    /**
     * 缓存信息
     */
    private RequestCache requestCache = new HttpSessionRequestCache();
    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private static final String URL_TAIL = ".html";

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 登录页可配置话说明
     * 1.如果是一般request 会引导用户去登录
     * 2.如果是request 是从html页面过来的则会重定向一个配置的页面，如果没有加配置会跳转到 默认登录页
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (Objects.nonNull(savedRequest)) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("======BrowserSecurityController requireAuthentication redirectUrl : {}", redirectUrl);
            if (StringUtils.endsWithIgnoreCase(redirectUrl, URL_TAIL)) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("请引导用户到等登陆页面");
    }



}
