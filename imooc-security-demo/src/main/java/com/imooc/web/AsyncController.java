package com.imooc.web;

import com.imooc.dto.DeferredResultHolder;
import com.imooc.dto.MockQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @author zhang.suxing
 * @date 2020/6/27 15:49
 **/
@RestController
@Slf4j
@RequestMapping("/order")
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    /**
     * 主线程已经返回 tomcat线程资源已经释放可以去处理其他请求
     * 提高服务起的吞吐量 业务逻辑处理交给副线程去处理
     */
    @GetMapping("/callable")
    public Callable<String> order1() {
        log.info("======AsyncController order 主线程开始");
        Callable<String> stringCallable = () -> {
            log.info("======AsyncController order 副线程开始");
            Thread.sleep(1000);
            log.info("======AsyncController order 副线程返回");
            return "order success";
        };
        log.info("======AsyncController order 主线程返回");
        return stringCallable;
    }

    @GetMapping("/deferred")
    public DeferredResult<String> order2() {
        log.info("======AsyncController order 主线程开始");
        String orderNum = "jeremy lin";
        mockQueue.setPlaceOrder(orderNum);
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNum, result);
        log.info("======AsyncController order 主线程返回");
        return result;

    }

}
