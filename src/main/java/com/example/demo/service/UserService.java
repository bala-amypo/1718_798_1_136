package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {

    User registerUser(String username, String password);

    String login(String username, String password);

    User getByUsername(String username);
}
