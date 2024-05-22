package com.example.webshoplabb.controllers;

import com.example.webshoplabb.dto.ProductDTO;
import com.example.webshoplabb.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-product")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            productService.createProduct(productDTO);
            return ResponseEntity.ok("Product created successfully");
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating category." + error.getMessage());
        }
    }
}
