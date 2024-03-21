package com.ecommerce.inventoryservice.exceptions;

import java.util.Map;

public class NotEnoughQuantityException extends RuntimeException{
    private Map<String, Integer> unavailableItems;
    public NotEnoughQuantityException (String msg,  Map<String, Integer> unavailableItems ){
        super(msg);
        this.unavailableItems=unavailableItems;
    }

    public Map<String, Integer> getUnavailableItems(){
        return this.unavailableItems;
    }

}