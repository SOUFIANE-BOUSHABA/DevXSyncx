package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.Task;
import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task findById(Long id);
    void save(Task task);
    void update(Task task);
    void delete(Long id);
}
