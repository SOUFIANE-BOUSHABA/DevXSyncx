package com.example.devxsyncx.service;

import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.repository.TokenRepository;
import com.example.devxsyncx.repository.UserRepository;
import com.example.devxsyncx.repository.impl.TokenRepositoryImpl;
import com.example.devxsyncx.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {


    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private TokenRepositoryImpl tokenRepository;

    @InjectMocks
    private UserService userService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        User user = new User();
        user.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(null);
        boolean result = userService.register(user);

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("test");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void login() {
    }

    @Test
    void checkPassword() {
    }

    @Test
    void deleteUserByUsername() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserById() {
    }
}