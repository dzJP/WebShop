package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.CategoryDTO;
import com.example.webshoplabb.dto.ProductDTO;
import com.example.webshoplabb.models.Product;
import com.example.webshoplabb.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public void createProduct(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getProduct(),
                productDTO.getPrice(),
                productDTO.getCategory()
        );
        productRepository.save(product);

    }
}
