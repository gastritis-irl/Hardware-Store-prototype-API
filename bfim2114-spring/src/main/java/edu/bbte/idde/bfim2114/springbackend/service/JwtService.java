package edu.bbte.idde.bfim2114.springbackend.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    String extractUsername(String token);

    Date extractExpiration(String token);

    Boolean isTokenExpired(String token);

    String generateToken(UserDetails userDetails);

    Boolean validateToken(String token, UserDetails userDetails);
}
