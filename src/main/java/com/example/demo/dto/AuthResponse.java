package com.example.demo.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String email;
    private Long userId;
    private String role;

    public AuthResponse(String token, String email, Long userId, String role) {
        this.token = token;
        this.email = email;
        this.userId = userId;
        this.role = role;
    }
}