package com.ecommerce.orderservice.model;


import java.time.Instant;
import java.util.List;
import com.ecommerce.orderservice.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateResponce {
    private String orderNumber;
    private Instant orderTime;
    private List<OrderItem> orderItems;
}
