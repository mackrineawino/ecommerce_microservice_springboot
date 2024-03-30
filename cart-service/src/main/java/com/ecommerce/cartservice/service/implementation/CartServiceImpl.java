package com.ecommerce.cartservice.service.implementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.model.CartCreateRequest;
import com.ecommerce.cartservice.model.CartCreateResponse;
import com.ecommerce.cartservice.repository.CartRepository;
import com.ecommerce.cartservice.service.CartService;

@Service
public class CartServiceImpl implements CartService{
    private CartRepository cartRepository;

    
     public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public List<CartCreateResponse> findAll() {
        return cartRepository.findAll().stream().map(this::mapToCartCreateResponse).toList();
    }

    private CartCreateResponse mapToCartCreateResponse(Cart source) {
        CartCreateResponse target = new CartCreateResponse();
        BeanUtils.copyProperties(source, target);
        return target;
    }


    @Override
    public CartCreateResponse addToCart(CartCreateRequest cartCreateRequest) {
        var savedCartItem =  cartRepository.save(mapToCartEntity(cartCreateRequest));
        return mapToCartCreateResponse(savedCartItem);
    }

    private Cart mapToCartEntity(CartCreateRequest source){
        Cart target = new Cart();
        BeanUtils.copyProperties(source, target);
        return target;

    }
}
