package com.ecommerce.cartservice.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartCreateRequest {
    private String name;
    private BigDecimal price;
    private String productCode;
    private String imageUrl;
    private Integer quantity;
}