package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.CategoryDTO;
import com.example.webshoplabb.models.Category;
import com.example.webshoplabb.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public void createCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getCategoryName());
        categoryRepository.save(category);

    }
}
