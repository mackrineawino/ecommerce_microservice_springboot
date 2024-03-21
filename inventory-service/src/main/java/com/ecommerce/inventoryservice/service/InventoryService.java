package com.ecommerce.inventoryservice.service;

import java.util.List;

import com.ecommerce.inventoryservice.model.InventoryRequest;
import com.ecommerce.inventoryservice.model.InventoryResponse;

public interface InventoryService {
    InventoryResponse createInventoryItem(InventoryRequest inventoryRequest);

    Boolean checkInventory(List<String> productCodes, List<Integer> productQuantities);
    boolean reduceInventory(String productCode, int quantity);
    
}
