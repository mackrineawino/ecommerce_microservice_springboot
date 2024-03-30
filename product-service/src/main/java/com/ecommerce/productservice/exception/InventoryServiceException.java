package com.ecommerce.productservice.exception;

public class InventoryServiceException extends RuntimeException {

    public InventoryServiceException(String message){
        super(message);
    }

}