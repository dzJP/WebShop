package com.example.webshoplabb.dto;

import com.example.webshoplabb.models.Order;
import com.example.webshoplabb.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Order order;
    private Product product;
    private int quantity;
    private double price;
}
