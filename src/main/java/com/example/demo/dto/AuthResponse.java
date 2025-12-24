package com.example.demo.dto;

public class AuthResponse {

    private String email;
    private String token;
    private String fullName;
    private String role;
    private Long userId;

    public AuthResponse() {}

    public AuthResponse(String email, String token, String fullName, String role, Long userId) {
        this.email = email;
        this.token = token;
        this.fullName = fullName;
        this.role = role;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
