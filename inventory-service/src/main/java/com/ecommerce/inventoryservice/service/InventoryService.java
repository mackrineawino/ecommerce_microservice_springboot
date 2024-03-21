package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.model.InventoryRequest;
import com.ecommerce.inventoryservice.model.InventoryResponse;

public interface InventoryService {
    InventoryResponse createInventoryItem(InventoryRequest inventoryRequest);
    
}
