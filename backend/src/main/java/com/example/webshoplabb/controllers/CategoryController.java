package com.example.webshoplabb.controllers;

import com.example.webshoplabb.dto.CategoryDTO;
import com.example.webshoplabb.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('USER') OR hasRole('Admin')")
    @GetMapping("/get-categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categoryList = categoryService.getCategories();
        return ResponseEntity.ok(categoryList);
    }
}
