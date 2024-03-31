package com.ecommerce.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.cartservice.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    void deleteById(Integer id);
}