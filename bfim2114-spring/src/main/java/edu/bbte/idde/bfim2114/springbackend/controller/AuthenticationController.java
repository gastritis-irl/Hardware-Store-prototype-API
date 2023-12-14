package edu.bbte.idde.bfim2114.springbackend.controller;

import edu.bbte.idde.bfim2114.springbackend.dto.LoginDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.RegisterDTO;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import edu.bbte.idde.bfim2114.springbackend.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
        log.info("POST: /api/register");
        User user = userService.registerUser(registerDTO);
        final ResponseEntity<?> jwt = getResponseEntity(user);
        if (jwt != null) return jwt;
        return ResponseEntity.badRequest().body("Registration failed");
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        log.info("POST: /api/login");
        User user = userService.validateUser(loginDTO);
        final ResponseEntity<?> jwt = getResponseEntity(user);
        if (jwt != null) return jwt;
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @Nullable
    private ResponseEntity<?> getResponseEntity(User user) {
        if (user != null) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .authorities(new ArrayList<>())
                .build();
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok().body(jwt);
        }
        return null;
    }

    @PostMapping("/api/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        log.info("POST: /api/refresh-token");
        String username = jwtUtil.extractUsername(token.substring(7));
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (jwtUtil.validateToken(token.substring(7), userDetails)) {
            String newToken = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok().body(newToken);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

}
