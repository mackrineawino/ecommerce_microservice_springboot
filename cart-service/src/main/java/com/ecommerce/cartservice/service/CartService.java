package com.ecommerce.cartservice.service;

import java.util.List;

import com.ecommerce.cartservice.model.CartCreateRequest;
import com.ecommerce.cartservice.model.CartCreateResponse;

public interface CartService {
      CartCreateResponse addToCart(CartCreateRequest cartCreateRequest);

    List<CartCreateResponse> findAll();

}
