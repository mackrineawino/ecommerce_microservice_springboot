package com.ecommerce.orderservice.service;

import java.util.List;

import com.ecommerce.orderservice.model.OrderCreateResponce;
import com.ecommerce.orderservice.model.OrderRequest;

public interface OrderService {

    String placeOrder(OrderRequest orderRequest);
    List<OrderCreateResponce> findAll();
    void deleteOrderById(Integer id);
    
}
