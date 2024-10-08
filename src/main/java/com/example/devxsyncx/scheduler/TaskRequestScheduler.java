package com.example.devxsyncx.scheduler;


import com.example.devxsyncx.entities.TaskRequest;
import com.example.devxsyncx.entities.Token;
import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.service.TaskRequestService;
import com.example.devxsyncx.service.TokenService;
import com.example.devxsyncx.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
public class TaskRequestScheduler {


    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final TaskRequestService taskRequestService;
    private final UserService userService;
    private final TokenService tokenService;

    public TaskRequestScheduler() {
        this.taskRequestService = new TaskRequestService();
        this.userService = new UserService();
        this.tokenService = new TokenService();

    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkAndUpdateTokenModifi, 0, 10, TimeUnit.SECONDS);
    }

    private void checkAndUpdateTokenModifi() {
        List<TaskRequest> overdueTasksRequest = taskRequestService.getAllTasks().stream()
                .filter(taskreq -> taskreq.getResponseDeadline().isBefore(LocalDateTime.now()) && !taskreq.isTokensIncremented())
                .collect(Collectors.toList());

        for (TaskRequest taskreq : overdueTasksRequest) {
            Token token = tokenService.findByUserId(taskreq.getRequester().getId());
            token.setModificationTokens(token.getModificationTokens() + 2);
            tokenService.update(token);
            taskreq.setTokensIncremented(true);
            taskRequestService.update(taskreq);
        }
    }

}
