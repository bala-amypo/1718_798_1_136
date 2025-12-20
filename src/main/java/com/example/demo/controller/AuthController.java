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
        System.out.println("DEBUG: Register called with user: " + user.getEmail());
        
        try {
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
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            
            User registeredUser = userService.register(user);
            System.out.println("DEBUG: User registered with ID: " + registeredUser.getId());
            
            // Generate token
            String token = jwtUtil.generateToken(
                registeredUser.getId(),
                registeredUser.getEmail(),
                registeredUser.getRole()
            );
            System.out.println("DEBUG: Token generated: " + token.substring(0, Math.min(token.length(), 20)) + "...");
            
            AuthResponse response = new AuthResponse(
                    token,
                    registeredUser.getId(),
                    registeredUser.getEmail(),
                    registeredUser.getRole(),
                    registeredUser.getFullName()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("DEBUG: Error in register: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        System.out.println("DEBUG: Login called for email: " + authRequest.getEmail());
        
        try {
            // Validate required fields
            if (authRequest.getEmail() == null || authRequest.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (authRequest.getPassword() == null || authRequest.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Password is required");
            }
            
            User user = userService.findByEmail(authRequest.getEmail());
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }
            
            boolean passwordMatches = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
            System.out.println("DEBUG: Password matches: " + passwordMatches);
            
            if (!passwordMatches) {
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
            
        } catch (Exception e) {
            System.err.println("DEBUG: Error in login: " + e.getMessage());
            throw e;
        }
    }
}