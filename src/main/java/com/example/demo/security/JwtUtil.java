package com.example.demo.security;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    public String generateToken(Map<String, Object> claims, String subject) {
        return subject + "-token";
    }
    public String generateToken(String subject) {
        return generateToken(new HashMap<>(), subject);
    }
}
