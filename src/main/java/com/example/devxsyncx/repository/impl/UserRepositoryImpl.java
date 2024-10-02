package com.example.devxsyncx.repository.impl;

import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class UserRepositoryImpl implements UserRepository {

    private EntityManagerFactory emf;

    public UserRepositoryImpl() {
        this.emf = Persistence.createEntityManagerFactory("myJPAUnit");
    }

    @Override
    public User findByUsername(String username) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(User user) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteByUsername(String username) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = findByUsername(username);
            if (user != null) {
                user = entityManager.merge(user);
                entityManager.remove(user);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void update(User user) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}