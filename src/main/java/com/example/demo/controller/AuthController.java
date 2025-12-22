package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final UserService userService;
    
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        User user = userService.findByEmail(authRequest.getEmail());
        String hashedPassword = "hashed_" + authRequest.getPassword();
        if (!user.getPassword().equals(hashedPassword)) {
            return ResponseEntity.status(401).build();
        }
        
        String token = "simple_token_" + user.getId();
        
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