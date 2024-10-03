package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.User;

import java.util.List;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
    void deleteByUsername(String username);
    void update(User user);

     List<User> getAllUsers();
     User getUserById(Long id);
}
