package com.example.devxsyncx.service;

import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.repository.UserRepository;
import com.example.devxsyncx.repository.impl.UserRepositoryImpl;
import jakarta.ejb.Stateless;

@Stateless
public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl(); // Create repository instance
    }

    public boolean register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public boolean checkPassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }


}
