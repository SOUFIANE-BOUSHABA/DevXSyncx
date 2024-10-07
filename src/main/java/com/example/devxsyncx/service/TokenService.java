package com.example.devxsyncx.service;

import com.example.devxsyncx.entities.Token;
import com.example.devxsyncx.repository.TokenRepository;
import com.example.devxsyncx.repository.impl.TokenRepositoryImpl;

public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService() {
        this.tokenRepository = new TokenRepositoryImpl();
    }

    public Token findByUserId(Long userId) {
        return tokenRepository.findByUserId(userId);
    }

    public void update(Token token) {
        tokenRepository.update(token);
    }
}
