package com.ecommerce.inventoryservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventoryservice.model.GenericResponse;
import com.ecommerce.inventoryservice.model.InventoryRequest;
import com.ecommerce.inventoryservice.model.InventoryResponse;
import com.ecommerce.inventoryservice.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("api/v1/inventory")
@CrossOrigin(origins = "http://localhost:1841", allowCredentials = "true")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    };

    @GetMapping("inventorylist")
    public GenericResponse<List<InventoryResponse>> list() {
       List<InventoryResponse> pr = inventoryService.findAll();
       GenericResponse<List<InventoryResponse>> resp = GenericResponse.<List<InventoryResponse>>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
                return resp;
    }
    @PostMapping("createInventory")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GenericResponse<InventoryResponse> createInventoryItem(
            @RequestBody InventoryRequest inventoryRequest) {
        log.info("We received : {}", inventoryRequest);
        InventoryResponse pr = inventoryService.createInventoryItem(inventoryRequest);
        GenericResponse<InventoryResponse> resp = GenericResponse.<InventoryResponse>builder()
                .success(true)
                .msg("Data saved Successfully")
                .data(pr)
                .build();
        log.info("We returned : {}", pr);
        return resp;
    }

    @GetMapping("check")
    @ResponseStatus(code = HttpStatus.OK)
    public GenericResponse<Boolean> checkInventory(
            @RequestParam(name = "productCodes") List<String> productCodes,
            @RequestParam(name = "productQuantities") List<Integer> productQuantities) {

        log.info("{}", productCodes);
        log.info("{}", productQuantities);
        return GenericResponse.<Boolean>builder()
                .data(inventoryService.checkInventory(productCodes, productQuantities))
                .success(true)
                .msg("Inventory exists/enough")
                .build();
    }

    @PutMapping("reduce")
    public GenericResponse<Boolean> reduceInventory(@RequestBody Map<String, Object> reduceRequest) {
        String productCode = (String) reduceRequest.get("productCode");
        int quantity = (int) reduceRequest.get("quantity");

        inventoryService.reduceInventory(productCode, quantity);

        return GenericResponse.<Boolean>builder()
        
                .success(true)
                .msg("Inventory updated Successfully")
                .build();

    }

    @PutMapping("updateInventory")
    public GenericResponse<Boolean> updateInventory(@RequestBody Map<String, Object> updateRequest) {
        String productCode = (String) updateRequest.get("productCode");
        Object quantityObj = updateRequest.get("newQuantity");
    
        if (quantityObj instanceof Integer) {
            int newQuantity = (int) quantityObj;
            inventoryService.updateInventory(productCode, newQuantity);
    
            return GenericResponse.<Boolean>builder()
                    .success(true)
                    .msg("Inventory updated Successfully")
                    .build();
        } else {
            // Handle the case where newQuantity is not an Integer
            return GenericResponse.<Boolean>builder()
                    .success(false)
                    .msg("Invalid newQuantity type")
                    .build();
        }
    }
    
}
