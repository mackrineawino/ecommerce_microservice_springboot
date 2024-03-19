package com.ecommerce.productservice.service;

import java.util.List;
import com.ecommerce.productservice.model.ProductCreateRequest;
import com.ecommerce.productservice.model.ProductCreateResponse;

public interface ProductService {
    ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest);

    List<ProductCreateResponse> findAll();

    ProductCreateResponse findById(Integer productId);
    
} 
