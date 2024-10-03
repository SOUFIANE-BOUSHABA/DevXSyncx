package com.example.devxsyncx.service;

import com.example.devxsyncx.entities.Tag;
import com.example.devxsyncx.repository.TagRepository;
import com.example.devxsyncx.repository.impl.TagRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class TagService {

    private TagRepository tagRepository;

    public TagService() {
        this.tagRepository = new TagRepositoryImpl();
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagsByIds(List<Long> tagIds) {
        return tagIds.stream()
                .map(tagRepository::findById)
                .collect(Collectors.toList());
    }
}
