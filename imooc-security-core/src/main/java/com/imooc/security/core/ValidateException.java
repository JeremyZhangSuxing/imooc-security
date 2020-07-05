package com.imooc.security.core;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zhang.suxing
 * @date 2020/7/5 15:54
 **/
public class ValidateException extends AuthenticationException {
    /**
     * Constructs an {@code AuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg the detail message
     * @param t   the root cause
     */
    public ValidateException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public ValidateException(String msg) {
        super(msg);
    }
}
