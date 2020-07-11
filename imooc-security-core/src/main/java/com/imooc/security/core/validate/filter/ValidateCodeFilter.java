package com.imooc.security.core.validate.filter;

import com.imooc.security.core.constants.SecurityConstants;
import com.imooc.security.core.exception.ValidateException;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.enums.ValidateCodeType;
import com.imooc.security.core.validate.processor.ValidateCodeProcessor;
import com.imooc.security.core.validate.processor.impl.ValidateProcessorHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * OncePerRequestFilter 这个过滤器每次只会被调用一次
 *
 * @author zhang.suxing
 * @date 2020/7/5 15:56
 **/
@Slf4j
@Data
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private static final String LOGIN_URL = "/authentication/form";

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Set<String> urlList = new HashSet<>(100);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateProcessorHolder validateProcessorHolder;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * 初始化ValidateFilter之后 设置url
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        log.info("=========================ValidateFilter is loading urls");
        super.afterPropertiesSet();
        //image 验证码
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE, ValidateCodeType.SMS);
    }

    /**
     * 处理登录时验证码校验
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType validateCodeType = getValidateCodeType(request);
        if (Objects.nonNull(validateCodeType)) {
            log.info("======ValidateFilter doFilterInternal validate request {} ,type {}", request.getRequestURI(),validateCodeType);
            try {
                ValidateCodeProcessor processor = validateProcessorHolder.findValidateCodeProcessor(validateCodeType);
                processor.validate(new ServletWebRequest(request));
            } catch (ValidateException e) {
                log.info("======ValidateFilter doFilterInternal fail {}", request.getRequestURI());
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

    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.GET.name())) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                //根据规则匹配  例如/* ---> /login
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
