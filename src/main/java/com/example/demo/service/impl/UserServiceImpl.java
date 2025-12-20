package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User register(User user) {
        // Check if user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }
    
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}