package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);
    
}
