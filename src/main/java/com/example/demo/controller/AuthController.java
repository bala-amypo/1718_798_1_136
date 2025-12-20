package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final JwtUtil jwtUtil;
    private final UserService userService; // Use interface
    private final PasswordEncoder passwordEncoder;
    
    public AuthController(JwtUtil jwtUtil, 
                         UserService userService, // Use interface
                         PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        // Validate required fields
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        
        // Default role to CUSTOMER if not specified
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("CUSTOMER");
        }
        
        // Validate role
        if (!user.getRole().equals("CUSTOMER") && !user.getRole().equals("ADMIN")) {
            throw new IllegalArgumentException("Role must be either CUSTOMER or ADMIN");
        }
        
        // Check if user already exists
        if (userService.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        User registeredUser = userService.register(user);
        
        // Generate token
        String token = jwtUtil.generateToken(
            registeredUser.getId(),
            registeredUser.getEmail(),
            registeredUser.getRole()
        );
        
        AuthResponse response = new AuthResponse(
                token,
                registeredUser.getId(),
                registeredUser.getEmail(),
                registeredUser.getRole(),
                registeredUser.getFullName()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        // Validate required fields
        if (authRequest.getEmail() == null || authRequest.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (authRequest.getPassword() == null || authRequest.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        
        User user = userService.findByEmail(authRequest.getEmail());
        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        
        // Generate token
        String token = jwtUtil.generateToken(
            user.getId(),
            user.getEmail(),
            user.getRole()
        );
        
        AuthResponse response = new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getFullName()
        );
        
        return ResponseEntity.ok(response);
    }
}