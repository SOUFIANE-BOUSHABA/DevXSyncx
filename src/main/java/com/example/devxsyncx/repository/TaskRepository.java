package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
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



    long countTasksByStatusAndManager(TaskStatus status, Long userId);

    List<Task> searchTasks(String search);


    List<Task> findTasksBy(TaskStatus status,  List<Long> tagIds, Long userId, LocalDateTime startDate, LocalDateTime endDate);


}
