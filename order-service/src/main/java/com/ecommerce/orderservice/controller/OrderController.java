package com.ecommerce.orderservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orderservice.model.GenericResponse;
import com.ecommerce.orderservice.model.OrderCreateResponce;
import com.ecommerce.orderservice.model.OrderRequest;
import com.ecommerce.orderservice.service.OrderService;

@RequestMapping("api/v1/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("placeOrder")
    public GenericResponse<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        GenericResponse<String> resp = GenericResponse.<String>builder()
                .success(true)
                .msg("Order placed successfully")
                .data(orderService.placeOrder(orderRequest))
                .build();
        return resp;
    }

      @GetMapping
    public GenericResponse<List<OrderCreateResponce>> list() {
       List<OrderCreateResponce> or = orderService.findAll();
       GenericResponse<List<OrderCreateResponce>> resp = GenericResponse.<List<OrderCreateResponce>>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(or)
                .build();
                return resp;
    }

}
