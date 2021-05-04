package com.pierre.dsvendas.entities.services;

import com.pierre.dsvendas.entities.Category;
import com.pierre.dsvendas.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {

        return categoryRepository.findAll();

    }
}
