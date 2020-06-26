package com.imooc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang.suxing
 * @date 2020/6/26 14:47
 *
 * 使用异常拦截器统一处理异常
 * 最好同一项目使用同意封装的异常 维护一套 message code
 **/
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExitException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserNotExitException(UserNotExitException ex) {
        Map<String, String> hashMap = new HashMap<>(16);
        hashMap.put("id", ex.getId());
        hashMap.put("message", ex.getMessage());
        return hashMap;
    }
}
