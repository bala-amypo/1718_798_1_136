package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoanDtos;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /* ================= REGISTER ================= */

    @PostMapping("/register")
    public LoanDtos.AuthResponse register(@RequestBody User user) {

        if (userService.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setRole("USER");

        User savedUser = userService.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail());

        LoanDtos.AuthResponse response =
                new LoanDtos.AuthResponse();
        response.setToken(token);
        response.setFullName(savedUser.getFullName());

        return response;
    }

    /* ================= LOGIN ================= */

    @PostMapping("/login")
    public LoanDtos.AuthResponse login(@RequestBody LoanDtos.AuthRequest request) {

        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        LoanDtos.AuthResponse response =
                new LoanDtos.AuthResponse();
        response.setToken(token);
        response.setFullName(user.getFullName());

        return response;
    }
}
