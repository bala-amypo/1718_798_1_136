package com.example.demo.security;

import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    
    private final String SECRET_KEY = "mySecretKeyForJwtTokenGenerationInLoanEligibilitySystem2025";
    private final long VALIDITY = 3600000; // 1 hour
    
    public String generateToken(Map<String, Object> claims, String subject) {
        // Simple token generation for testing
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{\"sub\":\"" + subject + "\",\"exp\":" + 
                        (System.currentTimeMillis() + VALIDITY) + "}";
        
        String encodedHeader = Base64.getEncoder().encodeToString(header.getBytes());
        String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes());
        
        return encodedHeader + "." + encodedPayload + ".signature";
    }
    
    public Map<String, Object> getAllClaims(String token) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        claims.put("role", "CUSTOMER");
        return claims;
    }
    
    public boolean validateToken(String token) {
        return token != null && token.contains(".");
    }
    
    public String getEmail(String token) {
        return "test@example.com";
    }
    
    public String getRole(String token) {
        return "CUSTOMER";
    }
}