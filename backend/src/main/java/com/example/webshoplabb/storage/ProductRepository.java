package com.example.webshoplabb.storage;
import com.example.webshoplabb.shop.Category;
import com.example.webshoplabb.shop.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    Optional<Product> findById(Long id);

    List<Product> findByCategory(Category category);
}
