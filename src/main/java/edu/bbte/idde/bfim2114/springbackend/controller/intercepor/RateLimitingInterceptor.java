package edu.bbte.idde.bfim2114.springbackend.controller.intercepor;

import edu.bbte.idde.bfim2114.springbackend.util.RateLimiterHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final RateLimiterHolder rateLimiterHolder;

    public RateLimitingInterceptor() {
        this.rateLimiterHolder = new RateLimiterHolder(100);
        log.info("RateLimitingInterceptor created");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws IOException {
        String clientIp = request.getRemoteAddr();

        log.info("RateLimitingInterceptor: {}", request.getRequestURI());

        if (!rateLimiterHolder.isAllowed(clientIp)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate Limit Exceeded from IP address: " + clientIp);
            return false;
        }

        return true;
    }
}
