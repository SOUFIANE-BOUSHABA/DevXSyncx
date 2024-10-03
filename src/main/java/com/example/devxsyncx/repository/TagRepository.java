package com.example.devxsyncx.repository;

import com.example.devxsyncx.entities.Tag;
import java.util.List;

public interface TagRepository {
    List<Tag> findAll();
    Tag findById(Long id);
}