package com.example.devxsyncx.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_requests")
public class TaskRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "response_deadline", nullable = false)
    private LocalDateTime responseDeadline;

    @Column(name = "tokens_incremented", nullable = false)
    private boolean tokensIncremented;

    public TaskRequest() {
        this.requestDate = LocalDateTime.now();
        this.responseDeadline = this.requestDate.plusHours(12);
    }

    public TaskRequest(Task task, User requester, User manager, LocalDateTime requestDate, LocalDateTime responseDeadline) {
        this.task = task;
        this.requester = requester;
        this.manager = manager;
        this.requestDate = requestDate;
        this.responseDeadline = responseDeadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getResponseDeadline() {
        return responseDeadline;
    }

    public void setResponseDeadline(LocalDateTime responseDeadline) {
        this.responseDeadline = responseDeadline;
    }



    public boolean isTokensIncremented() {
        return tokensIncremented;
    }

    public void setTokensIncremented(boolean tokensIncremented) {
        this.tokensIncremented = tokensIncremented;
    }
}
