package com.imooc.security.core.validate.filter;

import com.imooc.security.core.exception.ValidateException;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.dto.ImageCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * OncePerRequestFilter 这个过滤器每次只会被调用一次
 *
 * @author zhang.suxing
 * @date 2020/7/5 15:56
 **/
@Slf4j
@Data
public class ValidateFilter extends OncePerRequestFilter implements InitializingBean {
    private static final String LOGIN_URL = "/authentication/form";

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Set<String> urlList = new HashSet<>(100);

    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * 初始化ValidateFilter之后 设置url
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        log.info("=========================ValidateFilter is loading urls");
        super.afterPropertiesSet();
        String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        urlList.addAll(Arrays.asList(urls));
        urlList.add(LOGIN_URL);
    }

    /**
     * 处理登录时验证码校验
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean action = false;
        for (String v : urlList) {
            if (antPathMatcher.match(v, request.getRequestURI())) {
                action = true;
                break;
            }
        }
        if (action) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateException e) {
                log.info("======ValidateFilter doFilterInternal {}", request.getRequestURI());
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                //失败处理完直接返回
                return;
            }
        }
        //执行下一个filter
        filterChain.doFilter(request, response);
    }

    /**
     * 校验逻辑
     */
    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //生成验证码放入session的值
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, "");
        //登陆页面用户输入的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateException("验证码不能为空");
        }

        if (StringUtils.isEmpty(codeInSession.getCode())) {
            throw new ValidateException("验证码不存在，请重新获取");
        }
        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(servletWebRequest, "");
            throw new ValidateException("当前验证码已过期，请重新获取");
        }
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateException("验证码校验失败");
        }
        //validate success 删除session中的验证码
        sessionStrategy.removeAttribute(servletWebRequest, "");
    }
}
