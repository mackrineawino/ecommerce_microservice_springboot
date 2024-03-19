package com.ecommerce.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    
} 