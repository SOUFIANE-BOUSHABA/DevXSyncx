package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.User;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
}
