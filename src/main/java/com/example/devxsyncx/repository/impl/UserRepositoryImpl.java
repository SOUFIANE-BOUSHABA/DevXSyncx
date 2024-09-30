package com.example.devxsyncx.repository.impl;

import com.example.devxsyncx.entities.User;
import com.example.devxsyncx.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class UserRepositoryImpl implements UserRepository {

    private EntityManagerFactory emf;
    private EntityManager entityManager;

    public UserRepositoryImpl() {
        this.emf = Persistence.createEntityManagerFactory("myJPAUnit");
        this.entityManager = emf.createEntityManager();
    }

    @Override
    public User findByUsername(String username) {

        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }


}
