package com.imooc.service.impl;

import com.imooc.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhang.suxing
 * @date 2020/7/4 10:38
 **/
@Service
@Slf4j
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        log.info("++++++greeting");
        return "hello" + name;
    }
}
