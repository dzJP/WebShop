package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.CategoryDTO;
import com.example.webshoplabb.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {


    public List<CategoryDTO> getCategories() {
        return Arrays.stream(Category.values())
                .map(category -> new CategoryDTO(category.name()))
                .collect(Collectors.toList());
    }
}
