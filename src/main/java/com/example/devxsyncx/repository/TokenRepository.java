package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.Token;

import java.util.List;

public interface TokenRepository {
    void save(Token token);
    Token findByUserId(Long userId);
    void update(Token token);
    List<Token> findAll();
}