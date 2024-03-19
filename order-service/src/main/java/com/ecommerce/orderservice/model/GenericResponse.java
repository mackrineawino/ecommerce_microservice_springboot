package com.ecommerce.orderservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse<T> {
    private String msg;
    private boolean success;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;
    
}
    

