package com.ecommerce.inventoryservice.service.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.exceptions.NotEnoughQuantityException;
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
    @Override
    public boolean reduceInventory(String productCode, int quantity) {
        // Retrieve the current inventory level for the product
        Inventory inventoryItem = inventoryRepository.findByProductCode(productCode).orElse(null);
        
        if (inventoryItem == null || inventoryItem.getQuantity() < quantity) {
            // Not enough inventory available
            return false;
        }

        // Update the inventory level
        inventoryItem.setQuantity(inventoryItem.getQuantity() - quantity);
        inventoryRepository.save(inventoryItem);

        return true;
    }


       @Override
    public Boolean checkInventory(List<String> productCodes, List<Integer> productQuantities) {
        Map<String, Integer> unavailableItems = new HashMap<>();

        for (int i = 0; i < productCodes.size(); i++) {
            String productCode = productCodes.get(i);
            Integer productQuantity = productQuantities.get(i);
            Inventory inventory = inventoryRepository.findByProductCode(productCode).orElse(null);
            if (inventory != null) {
                // check if enough
                var dbInventory = inventory.getQuantity();
                if (productQuantity > dbInventory) {
                    unavailableItems.put(productCode, productQuantity - dbInventory);
                }
            } else {
                unavailableItems.put(productCode, productQuantity);
            }
        }
        if(unavailableItems.isEmpty()){
            return true;
        }else{
            throw new NotEnoughQuantityException("Not Enough Quantity in Stock", unavailableItems);
        }

    }

    
}
