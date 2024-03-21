package com.ecommerce.inventoryservice.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.model.InventoryRequest;
import com.ecommerce.inventoryservice.model.InventoryResponse;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService{

   private final InventoryRepository inventoryRepository;

    

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }



    @Override
    public InventoryResponse createInventoryItem(InventoryRequest inventoryRequest) {
        var savedInventoryItem =  inventoryRepository.save(mapToInventoryEntity(inventoryRequest));
        return mapToInventoryCreateResponse(savedInventoryItem);
    }

    private Inventory mapToInventoryEntity(InventoryRequest source){
        Inventory target = new Inventory();
        BeanUtils.copyProperties(source, target);
        return target;

    }
    private InventoryResponse mapToInventoryCreateResponse(Inventory source){
        InventoryResponse target = new InventoryResponse();
        BeanUtils.copyProperties(source, target);
        return target;

    }
    
}
