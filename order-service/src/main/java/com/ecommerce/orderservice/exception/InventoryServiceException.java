package com.ecommerce.orderservice.exception;

public class InventoryServiceException extends RuntimeException {

    public InventoryServiceException(String message){
        super(message);
    }

}