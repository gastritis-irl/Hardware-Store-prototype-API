package edu.bbte.idde.bfim2114.springbackend.config;

import edu.bbte.idde.bfim2114.springbackend.controller.filter.JwtRequestFilter;
import edu.bbte.idde.bfim2114.springbackend.controller.intercepor.LoggingInterceptor;
import edu.bbte.idde.bfim2114.springbackend.controller.intercepor.MetricsInterceptor;
import edu.bbte.idde.bfim2114.springbackend.controller.intercepor.RateLimitingInterceptor;
import edu.bbte.idde.bfim2114.springbackend.service.JwtService;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public RateLimitingInterceptor rateLimitingInterceptor() {
        return new RateLimitingInterceptor();
    }

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    public MetricsInterceptor metricsInterceptor() {
        return new MetricsInterceptor();
    }

    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(HttpMethod.GET,
                    "/api/user/**",
                    "/api/hardware/**",
                    "/api/category/**"
                ).permitAll()
                .requestMatchers(
                    "/api/login/**",
                    "/api/register/**",
                    "/api/refresh-token/**"
                ).permitAll()
                .requestMatchers(
                    HttpMethod.POST, "/api/hardware/**"
                ).hasAnyRole("USER", "ADMIN")
                .requestMatchers(
                    HttpMethod.PUT, "/api/hardware/**"
                ).hasAnyRole("USER", "ADMIN")
                .requestMatchers(
                    HttpMethod.DELETE, "/api/hardware/**"
                ).hasAnyRole("USER", "ADMIN")
                .requestMatchers(
                    HttpMethod.POST, "/api/logout/**"
                ).hasAnyRole("USER", "ADMIN")
                .requestMatchers(
                    "/**"
                ).hasRole("ADMIN")
                .anyRequest().authenticated(
                ))
            .addFilterBefore(new JwtRequestFilter(userService, jwtService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
