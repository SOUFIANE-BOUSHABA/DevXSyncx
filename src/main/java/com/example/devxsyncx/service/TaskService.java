package com.example.devxsyncx.service;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.enums.TaskStatus;
import com.example.devxsyncx.repository.TaskRepository;
import com.example.devxsyncx.repository.impl.TaskRepositoryImpl;

import java.util.ArrayList;
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



}