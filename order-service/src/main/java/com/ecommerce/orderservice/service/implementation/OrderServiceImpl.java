package com.ecommerce.orderservice.service.implementation;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.model.OrderCreateResponce;
import com.ecommerce.orderservice.model.OrderItemRequest;
import com.ecommerce.orderservice.model.OrderRequest;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
  
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @Override
    public void placeOrder(OrderRequest orderRequest) {
       Order order = new Order();
       order.setOrderNumber(UUID.randomUUID().toString());
       order.setOrderTime(Instant.now());
       var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();
       order.setOrderItems(orderItems);
       orderRepository.save(order);
    }
    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest){
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }

    @Override
    public List<OrderCreateResponce> findAll() {
        return orderRepository.findAll().stream().map(this::mapToOrderCreateResponce).toList();
    }
    private OrderCreateResponce mapToOrderCreateResponce(Order source){
        OrderCreateResponce target = new OrderCreateResponce();
        BeanUtils.copyProperties(source, target);
        return target;

    }

    
}
