package com.example.devxsyncx.repository.impl;

import com.example.devxsyncx.entities.Tag;
import com.example.devxsyncx.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class TagRepositoryImpl implements TagRepository {


    private EntityManagerFactory emf;

    public TagRepositoryImpl() {
        this.emf = Persistence.createEntityManagerFactory("myJPAUnit");
    }

    @Override
    public List<Tag> findAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Tag findById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Tag.class, id);
        } finally {
            entityManager.close();
        }
    }
}