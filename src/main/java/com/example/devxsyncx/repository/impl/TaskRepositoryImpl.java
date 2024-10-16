package com.example.devxsyncx.repository.impl;

import com.example.devxsyncx.entities.Tag;
import com.example.devxsyncx.entities.Task;
import com.example.devxsyncx.entities.enums.TaskStatus;
import com.example.devxsyncx.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    private EntityManagerFactory emf;

    public TaskRepositoryImpl() {
        this.emf = Persistence.createEntityManagerFactory("myJPAUnit");
    }

    @Override
    public List<Task> findAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Task t LEFT JOIN FETCH t.assignedTo LEFT JOIN FETCH t.createdBy", Task.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Task findById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Task.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(Task task) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            if (task.getTags() != null) {
                for (int i = 0; i < task.getTags().size(); i++) {
                    Tag tag = task.getTags().get(i);
                    if (tag.getId() != null) {
                        task.getTags().set(i, entityManager.merge(tag));
                    } else {
                        entityManager.persist(tag);
                    }
                }
            }
            entityManager.persist(task);

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }





    @Override
    public void update(Task task) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            if (!entityManager.contains(task)) {
                task = entityManager.merge(task);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }



    @Override
    public void delete(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Task task = entityManager.find(Task.class, id);
            if (task != null) {
                entityManager.remove(task);
            } else {
                System.out.println("Task not found with ID: " + id);
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
    public List<Task> findTasksByUser(Long userId) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.assignedTo.id = :userId", Task.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Task> findTasksByUserAndStatus(Long userId, TaskStatus status) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.assignedTo.id = :userId AND t.status = :status", Task.class)
                    .setParameter("userId", userId)
                    .setParameter("status", status)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public long countTasksByStatusAndUser(TaskStatus status, Long userId) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return (long) entityManager.createQuery(
                            "SELECT COUNT(t) FROM Task t WHERE t.status = :status AND t.assignedTo.id = :userId")
                    .setParameter("status", status)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }



    @Override
    public long countTasksByStatusAndManager(TaskStatus status, Long managerId) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return (long) entityManager.createQuery(
                            "SELECT COUNT(t) FROM Task t WHERE t.status = :status AND t.createdBy.id = :managerId")
                    .setParameter("status", status)
                    .setParameter("managerId", managerId)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }



    @Override
    public List<Task> searchTasks(String search) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.title LIKE :search OR t.description LIKE :search", Task.class)
                    .setParameter("search", "%" + search + "%")
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }



    @Override
    public List<Task> findTasksBy(TaskStatus status, List<Long> tagIds,Long userId,LocalDateTime startDate,LocalDateTime endDate){
        EntityManager entityManager = emf.createEntityManager();
        try {
            List<Task> tasks = entityManager.createQuery(
                            "SELECT t FROM Task t JOIN t.tags tag WHERE t.status = :status AND t.createdBy.id = :userId AND tag.id IN :tags AND t.createdAt BETWEEN :startDate AND :endDate", Task.class)
                    .setParameter("status", status)
                    .setParameter("userId", userId)
                    .setParameter("tags", tagIds)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
            return tasks;
        } finally {
            entityManager.close();
        }
    }
}
