package com.ecommerce.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.orderservice.model.GenericResponse;

@RestControllerAdvice
public class ControllerAdvise {

    @ExceptionHandler(InventoryServiceException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public GenericResponse<?> handleProductNotFound(InventoryServiceException ex){
            GenericResponse<?> resp = GenericResponse.builder()
                .success(false)
                .msg(ex.getMessage())
                .build();
                return resp;
    }
    @ExceptionHandler(NotEnoughQuantityException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public GenericResponse<?> handleProductNotFound(NotEnoughQuantityException ex){
            GenericResponse<?> resp = GenericResponse.builder()
                .success(false)
                .msg(ex.getMessage())
                .data(ex.getUnavailableItems())
                .build();
                return resp;
    }
    @ExceptionHandler(OrderServiceException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericResponse<?> handleOrderException(OrderServiceException ex){
            GenericResponse<?> resp = GenericResponse.builder()
                .success(false)
                .msg(ex.getMessage())
                .build();
                return resp;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericResponse<?> handleAnyOtherException(Exception ex){
            GenericResponse<?> resp = GenericResponse.builder()
                .success(false)
                .msg(ex.getMessage())
                .build();
                return resp;
    }

}
