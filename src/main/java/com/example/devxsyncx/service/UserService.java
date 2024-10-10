package com.example.devxsyncx.service;

import com.example.devxsyncx.entities.Token;
import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.repository.TokenRepository;
import com.example.devxsyncx.repository.UserRepository;
import com.example.devxsyncx.repository.impl.TokenRepositoryImpl;
import com.example.devxsyncx.repository.impl.UserRepositoryImpl;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {


    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
        this.tokenRepository = new TokenRepositoryImpl();
    }

    public boolean register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        Token token = new Token();
        token.setUser(user);
        user.addToken(token);

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


    public boolean updateUser(User user, String firstName, String lastName, String email, String currentPassword, String newPassword) {
        if (user.getPassword().equals(currentPassword)) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }

            userRepository.update(user);
            return true;
        } else {
            return false;
        }
    }



    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }


    public User getUserById(Long id){
        return userRepository.getUserById(id);
    }


}
