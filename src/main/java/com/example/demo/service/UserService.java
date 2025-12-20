package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    
    /**
     * Register a new user
     */
    User register(User user);
    
    /**
     * Save/update a user
     */
    User save(User user);
    
    /**
     * Find user by email
     */
    User findByEmail(String email);
    
    /**
     * Find user by ID
     */
    User findById(Long id);
    
    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);
}