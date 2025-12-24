package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> login(String username, String password);

    User register(User user);

    Map<String, Object> createClaims(User user);
}
