package edu.bbte.idde.bfim2114.springbackend.config;

import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import edu.bbte.idde.bfim2114.springbackend.util.JwtUtil;
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

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(HttpMethod.GET,
                    "/api/users/**",
                    "/api/hardware/**"
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
                    "/**"
                ).hasRole("ADMIN")
                .anyRequest().authenticated(
                ))
            .addFilterBefore(new JwtRequestFilter(userService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

}