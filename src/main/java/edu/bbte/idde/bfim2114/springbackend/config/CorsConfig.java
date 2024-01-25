package edu.bbte.idde.bfim2114.springbackend.config;

import edu.bbte.idde.bfim2114.springbackend.controller.intercepor.LoggingInterceptor;
import edu.bbte.idde.bfim2114.springbackend.controller.intercepor.MetricsInterceptor;
import edu.bbte.idde.bfim2114.springbackend.controller.intercepor.RateLimitingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

    private final RateLimitingInterceptor rateLimitingInterceptor;
    private final LoggingInterceptor loggingInterceptor;
    private final MetricsInterceptor metricsInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(metricsInterceptor);
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(rateLimitingInterceptor)
            .addPathPatterns("/api/**");
    }
}
