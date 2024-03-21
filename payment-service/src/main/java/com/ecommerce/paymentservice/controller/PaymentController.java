package com.ecommerce.paymentservice.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.paymentservice.model.GenericResponse;
import com.ecommerce.paymentservice.model.PaymentRequest;
import com.ecommerce.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public GenericResponse<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        boolean success = paymentService.processPayment(paymentRequest);
        if (success) {
            GenericResponse<String> resp = GenericResponse.<String>builder()
                    .success(true)
                    .msg("Payment processed successfully")
                    .build();
            return resp;
        } else {
            GenericResponse<String> resp = GenericResponse.<String>builder()
                    .success(false)
                    .msg("Failed to process payment")
                    .build();
            return resp;
        }
    }

}