package com.example.devxsyncx.repository.impl;

import com.example.devxsyncx.entities.TaskRequest;
import com.example.devxsyncx.repository.TaskRequestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TaskRequestRepositoryImpl implements TaskRequestRepository {
    private EntityManagerFactory emf;

    public TaskRequestRepositoryImpl() {
        this.emf = Persistence.createEntityManagerFactory("myJPAUnit");
    }

    @Override
    public List<TaskRequest> findTaskRequestsByUserId(Long userId) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT tr FROM TaskRequest tr WHERE tr.requester.id = :userId", TaskRequest.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<TaskRequest>findTaskRequestsByManagerId(Long managerId){
        EntityManager entityManager = emf.createEntityManager();
        try{
            return entityManager.createQuery(
                    "SELECT tr FROM TaskRequest tr where tr.manager.id = :managerId AND tr.responseDeadline >= CURRENT_TIMESTAMP   ", TaskRequest.class)
                    .setParameter("managerId", managerId)
                    .getResultList();

        }finally {
            entityManager.close();
        }
    }

    @Override
    public TaskRequest findById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(TaskRequest.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(TaskRequest taskRequest) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(taskRequest);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void update(TaskRequest taskRequest) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(taskRequest);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void delete(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            TaskRequest taskRequest = entityManager.find(TaskRequest.class, id);
            if (taskRequest != null) {
                entityManager.remove(taskRequest);
            } else {
                System.out.println("TaskRequest not found  " + id);
            }
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            entityManager.close();

        }
    }

    @Override
    public List<TaskRequest> findAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT tr FROM TaskRequest tr", TaskRequest.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
