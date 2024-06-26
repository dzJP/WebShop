package com.example.webshoplabb.repository;

import com.example.webshoplabb.models.Order;
import com.example.webshoplabb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
