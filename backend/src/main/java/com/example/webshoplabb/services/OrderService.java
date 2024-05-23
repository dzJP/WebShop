package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.OrderItemDTO;
import com.example.webshoplabb.models.Order;
import com.example.webshoplabb.models.OrderItem;
import com.example.webshoplabb.models.Product;
import com.example.webshoplabb.models.User;
import com.example.webshoplabb.repository.OrderRepository;
import com.example.webshoplabb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductService productService;


    @Transactional
    public void createOrder(String userEmail, List<OrderItemDTO> items) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = items.stream().map(dto -> {
            // Fetch product details from the database based on the product id
            Product product = productService.getProductById(dto.getId());
            if (product == null) {
                throw new RuntimeException("Product not found with id: " + dto.getId());
            }

            // Create OrderItem entity with product details
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            item.setPrice(dto.getPrice());
            return item;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        double totalAmount = calculateTotalAmount(orderItems);
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);
    }

    private double calculateTotalAmount(List<OrderItem> orderItems) {
        double total = 0.0;
        for (OrderItem item : orderItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public List<Order> getOrdersByUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        return orderRepository.findByUser(user);
    }
}
