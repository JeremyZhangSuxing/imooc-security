package com.imooc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author zhang.suxing
 * @date 2020/6/27 8:51
 **/
@Aspect
@Component
@Slf4j
public class TimeAspect {
    /**
     * 切面拦截的好处是可以拿到 invoke 方法的 名称和形参的值 可以做处理功能更强大
     * @param
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.imooc.web.UserController.*(..))")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        log.info("====== TimeAspect process start ");
        long start = System.currentTimeMillis();
        Object[] args = point.getArgs();
        Arrays.stream(args).forEach(v -> log.info("TimeAspect arg {}  name {}", v, v.getClass().getName()));
        Object proceed = point.proceed();
        log.info("======TimeAspect process end spend {} ms", System.currentTimeMillis() - start);
        return proceed;
    }
}
