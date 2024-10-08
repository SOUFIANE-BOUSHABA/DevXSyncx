package com.example.devxsyncx.service;

import com.example.devxsyncx.entities.TaskRequest;
import com.example.devxsyncx.repository.TaskRequestRepository;
import com.example.devxsyncx.repository.impl.TaskRequestRepositoryImpl;

import java.util.List;

public class TaskRequestService {
    private TaskRequestRepository taskRequestRepository;

    public TaskRequestService() {
        this.taskRequestRepository = new TaskRequestRepositoryImpl();
    }

    public void save(TaskRequest taskRequest) {
        taskRequestRepository.save(taskRequest);
    }


    public List<TaskRequest> findTaskRequestsByUserId(Long id) {
        return taskRequestRepository.findTaskRequestsByUserId(id);
    }

    public TaskRequest findById(Long id) {
        return taskRequestRepository.findById(id);
    }

    public void update(TaskRequest taskRequest) {
        taskRequestRepository.update(taskRequest);
    }

    public void delete(Long id) {
        taskRequestRepository.delete(id);
    }


    public List<TaskRequest> getAllTasks() {
        return taskRequestRepository.findAll();
    }
}