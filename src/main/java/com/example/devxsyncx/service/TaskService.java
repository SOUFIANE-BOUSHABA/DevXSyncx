package com.example.devxsyncx.service;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.enums.TaskStatus;
import com.example.devxsyncx.repository.TaskRepository;
import com.example.devxsyncx.repository.impl.TaskRepositoryImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepositoryImpl();
    }

    public List<Task> getAllTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public Task getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void createTask(Task task) {
        System.out.println("Creating task with tags: " + task.getTags());
        taskRepository.save(task);
    }


    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    public void deleteTask(Long id) {
        taskRepository.delete(id);
    }



    public List<Task> getPendingTasksForUser(Long userId) {
        return taskRepository.findTasksByUserAndStatus(userId, TaskStatus.PENDING);
    }

    public List<Task> getInProgressTasksForUser(Long userId) {
        return taskRepository.findTasksByUserAndStatus(userId, TaskStatus.IN_PROGRESS);
    }

    public List<Task> getCompletedTasksForUser(Long userId) {
        return taskRepository.findTasksByUserAndStatus(userId, TaskStatus.COMPLETED);
    }



    public long countTasksByStatusAndUser(TaskStatus status, Long userId) {
        return taskRepository.countTasksByStatusAndUser(status, userId);
    }


    public long countTasksByStatusAndManager(TaskStatus status, Long userId) {
        return taskRepository.countTasksByStatusAndManager(status, userId);
    }


    public List<Task> searchTasks(String search) {
        return taskRepository.searchTasks(search);
    }


    public long countFilteredTasks(TaskStatus status, Long userId, String[] tags, String period) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = LocalDateTime.now();

        if (period != null && !period.isEmpty()) {
            switch (period) {
                case "week":
                    startDate = endDate.minusWeeks(1);
                    break;
                case "month":
                    startDate = endDate.minusMonths(1);
                    break;
                case "year":
                    startDate = endDate.minusYears(1);
                    break;
            }
        }

        List<Long> tagIds = new ArrayList<>();
            for (String tag : tags) {
                tagIds.add(Long.parseLong(tag));
            }



        List<Task> tasks = taskRepository.findTasksBy(status, tagIds, userId, startDate, endDate);
        return tasks.size();
    }

}