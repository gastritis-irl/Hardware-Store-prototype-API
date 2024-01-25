package edu.bbte.idde.bfim2114.springbackend.controller.intercepor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        String method = request.getMethod();
        String path = request.getRequestURI();
        int status = response.getStatus();

        log.info("HTTP Request - Method: {}, URL: {}, Status: {}", method, path, status);
    }

}
