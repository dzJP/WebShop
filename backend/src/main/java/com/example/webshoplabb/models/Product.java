package com.example.webshoplabb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

}