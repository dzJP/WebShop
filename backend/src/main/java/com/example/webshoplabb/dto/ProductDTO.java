package com.example.webshoplabb.dto;

import com.example.webshoplabb.models.Category;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private Category category;
    private String image;
}
