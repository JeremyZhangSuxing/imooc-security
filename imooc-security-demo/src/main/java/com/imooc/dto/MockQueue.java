package com.imooc.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhang.suxing
 * @date 2020/6/27 16:12
 **/
@Slf4j
@Data
@Component
public class MockQueue {

    private String placeOrder;

    private String completeOrder;

    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            log.info("======MockQueue 接收到下单请求 {}", placeOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.info("======MockQueue sleep {}", e.getMessage());
            }
            this.completeOrder = placeOrder;
            log.info("======MockQueue 下单请求处理完成 {}", placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

}
