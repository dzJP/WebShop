package com.example.webshoplabb.controllers;

import com.example.webshoplabb.dto.OrderItemDTO;
import com.example.webshoplabb.models.Order;
import com.example.webshoplabb.models.OrderItem;
import com.example.webshoplabb.models.User;
import com.example.webshoplabb.services.OrderService;
import com.example.webshoplabb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/checkout")
    public ResponseEntity<String> createOrder(@RequestBody List<OrderItemDTO> items, @RequestParam String email) {
        try {
            orderService.createOrder(email, items);
            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating order: " + e.getMessage());
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            List<Order> orders = orderService.getOrdersByUserEmail(user.getEmail());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}