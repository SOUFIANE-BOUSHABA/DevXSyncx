package com.example.devxsyncx.scheduler;

import com.example.devxsyncx.entities.TaskRequest;
import com.example.devxsyncx.entities.Token;
import com.example.devxsyncx.service.TaskRequestService;
import com.example.devxsyncx.service.TokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ResetTokenScheduler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final TokenService tokenService;
    private final TaskRequestService taskRequestService;

    public ResetTokenScheduler() {
        this.tokenService = new TokenService();
        this.taskRequestService = new TaskRequestService();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::resetModificationTokens, 0, 1, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(this::resetDeletionTokens, 0, 10, TimeUnit.SECONDS);
    }

    private void resetModificationTokens() {
        List<Token> tokens = tokenService.getAllTokens().stream()
                .filter(token -> token.getLastReset().isBefore(LocalDateTime.now().minusSeconds(30)))
                .collect(Collectors.toList());

        for (Token token : tokens) {
            List<TaskRequest> userRequests = taskRequestService.findTaskRequestsByUserId(token.getUser().getId());
            long overdueRequestsCount = userRequests.stream()
                    .filter(taskRequest -> taskRequest.getResponseDeadline().isBefore(LocalDateTime.now().minusHours(12)) && !taskRequest.isTokensIncremented())
                    .count();

            if (overdueRequestsCount > 0) {
                token.setModificationTokens((int) (  2 * overdueRequestsCount * 2));
            } else {
                token.setModificationTokens(2);
            }

            token.setLastReset(LocalDateTime.now().plusDays(1));
            tokenService.update(token);


            for (TaskRequest taskRequest : userRequests) {
                if (taskRequest.getResponseDeadline().isBefore(LocalDateTime.now().minusHours(12)) && !taskRequest.isTokensIncremented()) {
                    taskRequest.setTokensIncremented(true);
                    taskRequestService.update(taskRequest);
                }
            }
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