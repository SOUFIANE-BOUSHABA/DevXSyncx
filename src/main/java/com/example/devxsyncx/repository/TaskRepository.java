package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.enums.TaskStatus;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task findById(Long id);
    void save(Task task);
    void update(Task task);
    void delete(Long id);


    List<Task> findTasksByUser(Long userId);
    List<Task> findTasksByUserAndStatus(Long userId, TaskStatus status);

    long countTasksByStatusAndUser(TaskStatus status, Long userId);


}
