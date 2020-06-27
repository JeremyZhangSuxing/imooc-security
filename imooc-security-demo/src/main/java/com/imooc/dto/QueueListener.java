package com.imooc.dto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zhang.suxing
 * @date 2020/6/27 16:32
 * <p>
 * ContextRefreshedEvent spring init 完成发送上下文加载完成事件 do refresh
 * eureka 心跳检测 调用接口push 此事件
 **/
@Slf4j
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true) {
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String orderNum = mockQueue.getCompleteOrder();
                    log.info("====== QueueListener is listen 订单的处理的结果 {}", orderNum);
                    deferredResultHolder.getMap().get(orderNum).setResult("place order success");
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.info("====== QueueListener exception {}", e.getMessage());
                    }
                }
            }
        }).start();
    }
}
