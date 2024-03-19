package com.ecommerce.orderservice.service;

import java.util.List;

import com.ecommerce.orderservice.model.OrderCreateResponce;
import com.ecommerce.orderservice.model.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

    List<OrderCreateResponce> findAll();
    
}
