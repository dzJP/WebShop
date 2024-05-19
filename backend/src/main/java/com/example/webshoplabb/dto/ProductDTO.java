package com.example.webshoplabb.dto;

import com.example.webshoplabb.models.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private double price;
    private Category category;

}
