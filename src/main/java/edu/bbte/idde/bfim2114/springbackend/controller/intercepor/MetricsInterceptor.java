package edu.bbte.idde.bfim2114.springbackend.controller.intercepor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class MetricsInterceptor implements HandlerInterceptor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicInteger requestCount = new AtomicInteger();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime.set(System.currentTimeMillis());
        requestCount.incrementAndGet();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        long duration = System.currentTimeMillis() - startTime.get();
        log.info("Request processing time: " + duration + "ms");
        log.info("Request count: " + requestCount.get());
        log.info("HTTP Method: " + request.getMethod());
        log.info("Request URI: " + request.getRequestURI());
        log.info("Client IP: " + request.getRemoteAddr());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        if (ex != null) {
            log.error("Exception occurred: ", ex);
        }
        log.info("Response status: " + response.getStatus());
    }
}
