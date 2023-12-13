package edu.bbte.idde.bfim2114.springbackend.controller;

import edu.bbte.idde.bfim2114.springbackend.dto.LoginDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.RegisterDTO;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import edu.bbte.idde.bfim2114.springbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        log.info("POST: /api/register");
        User user = userService.registerUser(registerDTO);
        if (user != null) {
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.badRequest().body("Registration failed");
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        log.info("POST: /api/login");
        User user = userService.validateUser(loginDTO);
        if (user != null) {
            final UserDetails userDetails = (UserDetails) userService.findByUsername(user.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(jwt);
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }
}
