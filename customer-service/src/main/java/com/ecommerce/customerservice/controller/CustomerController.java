package com.ecommerce.customerservice.controller;

import com.ecommerce.customerservice.model.CustomerCreateResponce;
import com.ecommerce.customerservice.model.CustomerRequest;
import com.ecommerce.customerservice.model.GenericResponse;
import com.ecommerce.customerservice.model.LoginRequest;
import com.ecommerce.customerservice.model.LoginResponse;
import com.ecommerce.customerservice.service.implementation.CustomerServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/customers")
@CrossOrigin(origins = "http://localhost:1841", allowCredentials = "true")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public GenericResponse<CustomerCreateResponce> signUp(@RequestBody CustomerRequest customerRequest) {
        CustomerCreateResponce response = customerService.signUp(customerRequest);
        return new GenericResponse<>("User registered successfully", true, response);
    }

    @PostMapping("/login")
    public GenericResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = customerService.login(loginRequest);
        if (response == null) {
            return new GenericResponse<>("Invalid credentials", false, null);
        } else {
            return new GenericResponse<>("Login successful", true, response);
        }
    }
}
