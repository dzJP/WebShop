package com.example.webshoplabb.controllers;

import com.example.webshoplabb.dto.ProductDTO;
import com.example.webshoplabb.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/get-products")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> productList = productService.getProducts();
        return ResponseEntity.ok(productList);
    }


}
