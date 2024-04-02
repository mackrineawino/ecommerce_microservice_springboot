package com.ecommerce.customerservice.service;

import com.ecommerce.customerservice.model.CustomerCreateResponce;
import com.ecommerce.customerservice.model.CustomerRequest;
import com.ecommerce.customerservice.model.LoginRequest;
import com.ecommerce.customerservice.model.LoginResponse;

public interface CustomerService {
LoginResponse login(LoginRequest loginRequest);
CustomerCreateResponce signUp(CustomerRequest customerRequest);
} 
