package com.ecommerce.cartservice.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.cartservice.model.CartCreateRequest;
import com.ecommerce.cartservice.model.CartCreateResponse;
import com.ecommerce.cartservice.model.GenericResponse;
import com.ecommerce.cartservice.service.CartService;


@RestController
@Slf4j
@RequestMapping("api/v1/cart")
@CrossOrigin(origins = "http://localhost:1841", allowCredentials = "true")
public class CartController {
    
private CartService cartService;

    public CartController(CartService cartService) {
    this.cartService = cartService;
}
    @GetMapping
    public GenericResponse<List<CartCreateResponse>> list() {
       List<CartCreateResponse> pr = cartService.findAll();
       GenericResponse<List<CartCreateResponse>> resp = GenericResponse.<List<CartCreateResponse>>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
                return resp;
    }
    @PostMapping
    public GenericResponse<CartCreateResponse> addToCart(
            @RequestBody CartCreateRequest cartCreateRequest) {
                log.info("We received : {}",cartCreateRequest);
                CartCreateResponse pr = cartService.addToCart(cartCreateRequest);
        GenericResponse<CartCreateResponse> resp = GenericResponse.<CartCreateResponse>builder()
                .success(true)
                .msg("Data saved Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
        return resp;
    }


}
