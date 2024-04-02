package com.ecommerce.customerservice.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ecommerce.customerservice.entity.Customer;
import com.ecommerce.customerservice.model.CustomerCreateResponce;
import com.ecommerce.customerservice.model.CustomerRequest;
import com.ecommerce.customerservice.repository.CustomerRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.ecommerce.customerservice.model.LoginRequest;
import com.ecommerce.customerservice.model.LoginResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl {

    private CustomerRepository customerRepository;
    
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerCreateResponce signUp(CustomerRequest customerRequest){
        Customer savedProduct = customerRepository.save(mapToCustomerEntity(customerRequest));
        return mapToCustomerCreateResponse(savedProduct);
    }
    
    public LoginResponse login(LoginRequest loginRequest){
        Customer customer = customerRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (customer == null) {
            return null; 
        }
        
        String token = generateToken(customer.getUsername());
        
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(customer.getUsername());
        loginResponse.setPassword(customer.getPassword());
        loginResponse.setToken(token);
        
        return loginResponse;
    }
    
    private Customer mapToCustomerEntity(CustomerRequest source) {
        Customer target = new Customer();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private CustomerCreateResponce mapToCustomerCreateResponse(Customer source) {
        CustomerCreateResponce target = new CustomerCreateResponce();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private String generateToken(String username) {
        long expirationTime = 1000 * 60 * 60; 
        String secretKey = "ThisIsASecretKeyForJWTTokenGeneration"; 
        
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
