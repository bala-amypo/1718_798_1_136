package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(UserService userService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        User saved = userService.register(user);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        Optional<User> found = userRepository.findByEmail(req.getEmail());

        if (found.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        User user = found.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(null);
        }

        String token = jwtUtil.generateToken(userService.createClaims(user), user.getEmail());

        AuthResponse resp = new AuthResponse(user.getEmail(), token, user.getFullName(), user.getRole(), user.getId());
        return ResponseEntity.ok(resp);
    }
}
