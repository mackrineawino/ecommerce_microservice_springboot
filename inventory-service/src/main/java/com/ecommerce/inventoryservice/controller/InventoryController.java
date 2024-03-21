package com.ecommerce.inventoryservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventoryservice.model.GenericResponse;
import com.ecommerce.inventoryservice.model.InventoryRequest;
import com.ecommerce.inventoryservice.model.InventoryResponse;
import com.ecommerce.inventoryservice.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    };
    

    @PostMapping("createInventory")
    public GenericResponse<InventoryResponse> createInventoryItem(
            @RequestBody InventoryRequest inventoryRequest) {
                log.info("We received : {}",inventoryRequest);
                InventoryResponse pr = inventoryService.createInventoryItem(inventoryRequest);
        GenericResponse<InventoryResponse> resp = GenericResponse.<InventoryResponse>builder()
                .success(true)
                .msg("Data saved Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
        return resp;
    }
    
}
