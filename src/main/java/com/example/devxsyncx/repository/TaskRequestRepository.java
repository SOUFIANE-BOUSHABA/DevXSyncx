package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.TaskRequest;

import java.util.List;

public interface TaskRequestRepository {
    List<TaskRequest> findTaskRequestsByUserId(Long id);
    TaskRequest findById(Long id);
    void save(TaskRequest taskRequest);
    void update(TaskRequest taskRequest);
    void delete(Long id);

    List<TaskRequest> findAll();
}
