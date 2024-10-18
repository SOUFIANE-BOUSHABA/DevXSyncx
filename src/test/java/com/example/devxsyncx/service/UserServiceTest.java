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

        when(userRepository.findByUsername("test")).thenReturn(null );
        boolean result = userService.register(user);

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("test");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void login() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("password");

        when(userRepository.findByUsername("test")).thenReturn(user);
        User result = userService.login("test", "password");

        assertEquals(user, result);
        verify(userRepository, times(1)).findByUsername("test");
    }

    @Test
    void checkPassword() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("password");

        when(userRepository.findByUsername("test")).thenReturn(user);
        boolean result = userService.checkPassword("test", "password");

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("test");
    }

    @Test
    void deleteUserByUsername() {
        String username = "test";

        doNothing().when(userRepository).deleteByUsername(username);
        userService.deleteUserByUsername(username);

        verify(userRepository, times(1)).deleteByUsername(username);
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());

        when(userRepository.getAllUsers()).thenReturn(users);
        List<User> result = userService.getAllUsers();

        assertEquals(users, result);
        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setId(1L);

        when(userRepository.getUserById(1L)).thenReturn(user);
        User result = userService.getUserById(1L);

        assertEquals(user, result);
        verify(userRepository, times(1)).getUserById(1L);
    }
}