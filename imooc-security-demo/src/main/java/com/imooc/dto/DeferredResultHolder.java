package com.imooc.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhang.suxing
 * @date 2020/6/27 16:29
 **/
@Component
@Data
public class DeferredResultHolder {
    private Map<String, DeferredResult<String>> map = new ConcurrentHashMap<>(16);
}
