package com.example.devxsyncx.scheduler;
import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.enums.TaskStatus;
import com.example.devxsyncx.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TaskScheduler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final TaskService taskService;

    public TaskScheduler() {
        this.taskService = new TaskService();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkAndUpdateTasks, 0, 10, TimeUnit.SECONDS);
    }

    private void checkAndUpdateTasks() {
        List<Task> overdueTasks = taskService.getAllTasks().stream()
                .filter(task -> !task.isCompleted() && task.getDueDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

        for (Task task : overdueTasks) {
            task.setStatus(TaskStatus.OVERDUE);
            taskService.updateTask(task);
        }
    }

}
