package com.imooc.security.core.validate.filter;

import com.imooc.security.core.ValidateException;
import com.imooc.security.core.controller.ValidateCodeController;
import com.imooc.security.core.validate.dto.ImageCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter 这个过滤器每次只会被调用一次
 *
 * @author zhang.suxing
 * @date 2020/7/5 15:56
 **/
@Slf4j
@Data
//@Component
public class ValidateFilter extends OncePerRequestFilter {
    private static final String LOGIN_URL = "/authentication/form";

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 处理登录时验证码校验
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals(LOGIN_URL, request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.POST.name())) {
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
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
        //登陆页面用户输入的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateException("验证码不能为空");
        }

        if (StringUtils.isEmpty(codeInSession.getCode())) {
            throw new ValidateException("验证码不存在，请重新获取");
        }
        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
            throw new ValidateException("当前验证码已过期，请重新获取");
        }
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateException("验证码校验失败");
        }
        //validate success 删除session中的验证码
        sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
    }
}
