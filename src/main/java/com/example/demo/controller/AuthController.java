package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        User user = userService.findByEmail(authRequest.getEmail());
        
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        claims.put("email", user.getEmail());

        String token = jwtUtil.generateToken(claims, user.getEmail());
        
        AuthResponse response = new AuthResponse(token, user.getEmail(), user.getId(), user.getRole());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        // Create a new user
        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setPassword(authRequest.getPassword());
        user.setFullName("New User"); // Default name
        
        User registeredUser = userService.register(user);
        
        // Generate token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", registeredUser.getId());
        claims.put("role", registeredUser.getRole());
        claims.put("email", registeredUser.getEmail());

        String token = jwtUtil.generateToken(claims, registeredUser.getEmail());
        
        AuthResponse response = new AuthResponse(token, registeredUser.getEmail(), 
                registeredUser.getId(), registeredUser.getRole());
        return ResponseEntity.ok(response);
    }
}