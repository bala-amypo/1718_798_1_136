package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
// Remove: @Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {
    
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    public AuthController(JwtUtil jwtUtil, 
                         UserService userService,
                         PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        // Default role to CUSTOMER if not specified
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("CUSTOMER");
        }
        
        User registeredUser = userService.register(user);
        
        // Generate token
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", registeredUser.getEmail());
        claims.put("role", registeredUser.getRole());
        claims.put("userId", registeredUser.getId());
        
        String token = jwtUtil.generateToken(claims, registeredUser.getEmail());
        
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
        User user = userService.findByEmail(authRequest.getEmail());
        
        // Check password
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        // Generate token
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        claims.put("userId", user.getId());
        
        String token = jwtUtil.generateToken(claims, user.getEmail());
        
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