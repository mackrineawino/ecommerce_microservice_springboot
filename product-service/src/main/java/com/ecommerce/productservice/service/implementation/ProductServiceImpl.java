package com.ecommerce.productservice.service.implementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.InventoryServiceException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.model.GenericResponse;
import com.ecommerce.productservice.model.InventoryRequest;
import com.ecommerce.productservice.model.ProductCreateRequest;
import com.ecommerce.productservice.model.ProductCreateResponse;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
     private final WebClient.Builder webClientBuilder;
   

    public ProductServiceImpl(ProductRepository productRepository, Builder webClientBuilder) {
        this.productRepository = productRepository;
        this.webClientBuilder = webClientBuilder;
    }


    @Override
    public ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest) {
        Product savedProduct = productRepository.save(mapToProductEntity(productCreateRequest));
    
        // Create the request body
        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setProductCode(savedProduct.getProductCode());
        inventoryRequest.setQuantity(productCreateRequest.getQuantity());
    
        // Make a POST request to the inventory service to create inventory entry
        GenericResponse<?> response = webClientBuilder.build().post()
            .uri("http://inventory-service/api/v1/inventory/createInventory")
            .body(Mono.just(inventoryRequest), InventoryRequest.class)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<GenericResponse<?>>() {})
            .block();
    
        return mapToProductCreateResponse(savedProduct);
    }
    
    private Product mapToProductEntity(ProductCreateRequest source) {
        Product target = new Product();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private ProductCreateResponse mapToProductCreateResponse(Product source) {
        ProductCreateResponse target = new ProductCreateResponse();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public List<ProductCreateResponse> findAll() {
        return productRepository.findAll().stream().map(this::mapToProductCreateResponse).toList();
    }

    @Override
    public ProductCreateResponse findById(Integer productId) {
        var productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return mapToProductCreateResponse(productOptional.get());
        }
        throw new ProductNotFoundException("Product with id " + productId + " not found");
    }
}
