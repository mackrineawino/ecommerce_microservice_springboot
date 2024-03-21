package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.model.PaymentRequest;

public interface PaymentService {
    public boolean processPayment(PaymentRequest paymentRequest);
    
} 