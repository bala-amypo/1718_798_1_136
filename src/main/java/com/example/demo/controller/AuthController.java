package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {

        User user = userService.findByEmail(req.getEmail());

        String token = jwtUtil.generateToken(
                java.util.Map.of(
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "userId", user.getId()
                ),
                user.getEmail()
        );

        return ResponseEntity.ok(
                new AuthResponse(token, user.getEmail())
        );
    }
}
