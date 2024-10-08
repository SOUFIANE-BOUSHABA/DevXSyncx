package com.example.devxsyncx.repository.impl;

import com.example.devxsyncx.entities.Token;
import com.example.devxsyncx.repository.TokenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TokenRepositoryImpl implements TokenRepository {

    private EntityManagerFactory emf;

    public TokenRepositoryImpl() {
        this.emf = Persistence.createEntityManagerFactory("myJPAUnit");
    }

    @Override
    public void save(Token token) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(token);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }




    @Override
    public Token findByUserId(Long userId) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM Token t WHERE t.user.id = :userId", Token.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Token token) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(token);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<Token> findAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM Token t", Token.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}