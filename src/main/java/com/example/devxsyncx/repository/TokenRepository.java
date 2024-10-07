package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.Token;

public interface TokenRepository {
    void save(Token token);
    Token findByUserId(Long userId);
    void update(Token token);
}