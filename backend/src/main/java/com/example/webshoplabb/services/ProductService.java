package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.CategoryDTO;
import com.example.webshoplabb.dto.ProductDTO;
import com.example.webshoplabb.models.Product;
import com.example.webshoplabb.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public void createProduct(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getCategory()
        );
        productRepository.save(product);

    }

    public List<ProductDTO> getProducts() {
        List<Product> productList = productRepository.findAll();

        System.out.printf("message: " + productList);
        return productList.stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setName(product.getName());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setCategory(product.getCategory());

                    return productDTO;
                })
                .collect(Collectors.toList());
    }
}
