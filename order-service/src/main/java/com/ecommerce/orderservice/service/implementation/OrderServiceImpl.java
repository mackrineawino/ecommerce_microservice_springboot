package com.ecommerce.orderservice.service.implementation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.model.GenericResponse;
import com.ecommerce.orderservice.model.OrderCreateResponce;
import com.ecommerce.orderservice.model.OrderItemRequest;
import com.ecommerce.orderservice.model.OrderRequest;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
  
    private final OrderRepository orderRepository;
      private final WebClient webClient;

      public OrderServiceImpl(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }


    @Override
    public void placeOrder(OrderRequest orderRequest) {
       Order order = new Order();
        List<String> productCodes = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            productCodes.add(orderItemRequest.getProductCode());
            productQuantities.add(orderItemRequest.getQuantity());
        }
        log.info("{}",productCodes);       
        log.info("{}",productQuantities);  
        GenericResponse<Boolean> response = webClient.get()
                .uri("http://localhost:6002/api/v1/inventory/check",
                        uriBuilder -> uriBuilder
                                .queryParam("productCodes", productCodes)
                                .queryParam("productQuantities", productQuantities)
                                .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<Boolean>>() {
                })
                .block();
        if (response.isSuccess()) {
            // stock
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderTime(Instant.now());
            var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();
            order.setOrderItems(orderItems);
            orderRepository.save(order);
        }else{
          // ! throw an exception with the listing of the products that do have enough
          log.error("Not Enough stock");
        }

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
