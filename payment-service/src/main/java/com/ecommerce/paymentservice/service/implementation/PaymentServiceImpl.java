package com.ecommerce.paymentservice.service.implementation;

import org.springframework.stereotype.Service;

import com.ecommerce.paymentservice.model.PaymentRequest;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean processPayment(PaymentRequest paymentRequest) {
        System.out.println("Payment made");
        return true;
    }
    
}
