package com.ecommerce.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    
}
