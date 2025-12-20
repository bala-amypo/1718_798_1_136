package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User register(User user);
    User save(User user);
    User findByEmail(String email);
    User findById(Long id);
    boolean existsByEmail(String email);
    
    // Add these methods for test requirements
    User registerUser(User user);
    User getUserById(Long id);
}