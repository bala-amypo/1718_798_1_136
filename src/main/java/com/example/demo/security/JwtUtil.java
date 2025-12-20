package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    
    private final SecretKey secretKey;
    private final long validityInMilliseconds;
    
    public JwtUtil(
            @Value("${jwt.secret:your-very-secret-key-change-this-in-production-minimum-32-characters}") 
            String secret,
            @Value("${jwt.validity:3600000}") 
            long validityInMs) {
        
        // Ensure the secret is at least 32 characters
        String actualSecret = secret;
        if (secret.length() < 32) {
            // Pad the secret if it's too short
            actualSecret = String.format("%-32s", secret).substring(0, 32);
        }
        
        this.secretKey = Keys.hmacShaKeyFor(actualSecret.getBytes());
        this.validityInMilliseconds = validityInMs;
    }
    
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getEmail(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    public String getRole(String token) {
        Claims claims = extractAllClaims(token);
        Object role = claims.get("role");
        return role != null ? role.toString() : null;
    }
    
    public Long getUserId(String token) {
        Claims claims = extractAllClaims(token);
        Object userId = claims.get("userId");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }
        return userId != null ? Long.parseLong(userId.toString()) : null;
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}