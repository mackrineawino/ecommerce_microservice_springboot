package com.ecommerce.productservice.model;

import java.math.BigDecimal;

import com.ecommerce.productservice.entity.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateResponse {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String productCode;
    private ProductCategory productCategory;
    private String imageUrl;
}
