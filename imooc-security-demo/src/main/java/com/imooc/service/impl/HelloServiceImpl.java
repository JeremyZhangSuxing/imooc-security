package com.imooc.service.impl;

import com.imooc.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhang.suxing
 * @date 2020/6/25 23:44
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
