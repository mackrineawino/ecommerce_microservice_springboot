package com.ecommerce.inventoryservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.inventoryservice.entity.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String>{

    Optional<Inventory> findByProductCode(String productCode);

    
}