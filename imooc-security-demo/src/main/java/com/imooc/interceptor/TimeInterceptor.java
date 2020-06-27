package com.imooc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author zhang.suxing
 * @date 2020/6/26 15:22
 * <p>
 * 只声明这个组件不起作用 需要额外的 配置 {@link com.imooc.config.WebConfig}
 * 会拦截所有的 controller 包括spring 自己的 example BasicErrorController
 *
 **/
@Component
@Slf4j
public class TimeInterceptor implements HandlerInterceptor {

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用。
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  当前命中的控制器
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String controllerName = ((HandlerMethod) handler).getBean().getClass().getName();
        String methodName = ((HandlerMethod) handler).getMethod().getName();
        request.setAttribute("start", System.currentTimeMillis());
        log.info("======TimeInterceptor preHandle request param  controller {}, methodName {}", controllerName, methodName);
        return true;
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。
     * postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操作。
     *
     * @param request      current HTTP request
     * @param response     current HTTP response
     * @param handler      当前命中的控制器
     * @param modelAndView that the handler returned
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("======TimeInterceptor postHandle======");
        long start = (long) request.getAttribute("start");
        log.info("======TimeInterceptor postHandle spend time = " + (System.currentTimeMillis() - start));
    }

    /**
     * 目标方法执行完成之后该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行。
     * （这个方法的主要作用是用于清理资源的）
     * 如果有异常抛出 不会在这里被拿到，因为被{@link com.imooc.exception.ControllerExceptionHandler} 处理掉了
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  当前命中的控制器e
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("======TimeInterceptor afterCompletion======异常信息 {}", Objects.isNull(ex) ? "no exception" : ex.getMessage());
    }
}
