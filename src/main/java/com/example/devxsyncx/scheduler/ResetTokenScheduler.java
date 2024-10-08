package com.example.devxsyncx.scheduler;


import com.example.devxsyncx.entities.Token;
import com.example.devxsyncx.service.TokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ResetTokenScheduler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private TokenService tokenService;

    public ResetTokenScheduler() {
        this.tokenService = new TokenService();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::resetModificationTokens, 0, 10, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::resetDeletionTokens, 0, 10, TimeUnit.SECONDS);
    }

    private void resetModificationTokens() {
        List<Token> tokens = tokenService.getAllTokens().stream()
                .filter(token -> token.getLastReset().isBefore(LocalDateTime.now().minusSeconds(30)))
                .collect(Collectors.toList());

        for (Token token : tokens) {
            token.setModificationTokens(2);
            token.setLastReset(LocalDateTime.now());
            tokenService.update(token);
        }
    }

    private void resetDeletionTokens() {
        List<Token> tokens = tokenService.getAllTokens().stream()
                .filter(token -> token.getLastReset().isBefore(LocalDateTime.now().minusDays(30)))
                .collect(Collectors.toList());

        for (Token token : tokens) {
            token.setDeletionTokens(1);
            token.setLastReset(LocalDateTime.now());
            tokenService.update(token);
        }
    }

}
