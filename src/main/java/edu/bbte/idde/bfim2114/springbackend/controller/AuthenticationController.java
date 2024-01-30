package edu.bbte.idde.bfim2114.springbackend.controller;

import edu.bbte.idde.bfim2114.springbackend.dto.LoginDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.RegisterDTO;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.service.JwtService;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserService userService;

    private final JwtService jwtService;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
        User user = userService.registerUser(registerDTO);
        final ResponseEntity<?> jwt = getResponseEntity(user);
        if (jwt != null) {
            return jwt;
        }
        return ResponseEntity.badRequest().body("Registration failed");
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.validateUser(loginDTO);
        final ResponseEntity<?> jwt = getResponseEntity(user);
        if (jwt != null) {
            return jwt;
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @Nullable
    private ResponseEntity<?> getResponseEntity(User user) {
        if (user != null) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();
            final String jwt = jwtService.generateToken(userDetails);

            return createResponseEntity(jwt, userDetails, user);
        }
        return null;
    }

    private ResponseEntity<?> createResponseEntity(String jwt, UserDetails userDetails, User user) {
        Map<String, Object> responseData = new ConcurrentHashMap<>();
        responseData.put("token", jwt);
        responseData.put("role", user.getRole());
        responseData.put("email", userDetails.getUsername());
        responseData.put("expirationDate", jwtService.extractExpiration(jwt));
        responseData.put("id", user.getId());
        responseData.put("themeId", user.getThemeId());
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping("/api/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        String username = jwtService.extractUsername(token.substring(7));
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (jwtService.validateToken(token.substring(7), userDetails)) {
            String newToken = jwtService.generateToken(userDetails);
            return createResponseEntity(newToken, userDetails, user);
        } else {
            if (jwtService.isTokenExpired(token.substring(7)) && username.equals(userDetails.getUsername())) {
                log.info("Token is expired, generating new token");
                String newToken = jwtService.generateToken(userDetails);
                return createResponseEntity(newToken, userDetails, user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String username = jwtService.extractUsername(token.substring(7));
        log.info("User " + username + " logged out.");

        return ResponseEntity.noContent().build();
    }


}
