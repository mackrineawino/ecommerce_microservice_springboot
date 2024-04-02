package com.ecommerce.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.customerservice.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByUsernameAndPassword(String username, String password);

    
} 
