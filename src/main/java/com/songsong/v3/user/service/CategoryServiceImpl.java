package com.songsong.v3.user.service;

import com.songsong.v3.user.entity.Category;
import com.songsong.v3.user.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id);
    }
}
