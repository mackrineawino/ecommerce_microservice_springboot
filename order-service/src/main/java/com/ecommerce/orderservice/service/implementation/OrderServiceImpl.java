package com.ecommerce.orderservice.service.implementation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.Instant;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.exception.InventoryServiceException;
import com.ecommerce.orderservice.exception.NotEnoughQuantityException;
import com.ecommerce.orderservice.exception.OrderServiceException;
import com.ecommerce.orderservice.model.GenericResponse;
import com.ecommerce.orderservice.model.OrderCreateResponce;
import com.ecommerce.orderservice.model.OrderItemRequest;
import com.ecommerce.orderservice.model.OrderRequest;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
  
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderServiceImpl(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }


    @SuppressWarnings("unchecked")
    @Override
    public String placeOrder(OrderRequest orderRequest) {
       Order order = new Order();
        List<String> productCodes = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            productCodes.add(orderItemRequest.getProductCode());
            productQuantities.add(orderItemRequest.getQuantity());
        }
        log.info("{}",productCodes);       
        log.info("{}",productQuantities);  
        GenericResponse<?> response = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory/check",
                        uriBuilder -> uriBuilder
                                .queryParam("productCodes", productCodes)
                                .queryParam("productQuantities", productQuantities)
                                .build())
                .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> handleError(clientResponse))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<?>>() {
                })
                .block();
        if (response.isSuccess()) {
            // stock
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderTime(Instant.now());
            var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();
            order.setOrderItems(orderItems);
            orderRepository.save(order);
            for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
                reduceInventory(orderItemRequest.getProductCode(), orderItemRequest.getQuantity());
            }
        
            return order.getOrderNumber();
        }else {
            // ! throw an exception with the listing of the products that do have enough
            log.error("Not Enough stock");
            log.info("{}", response.getData());
            if(response.getData() instanceof Map){
                throw new NotEnoughQuantityException(response.getMsg(),(Map<String, Integer>) response.getData());
            }
            throw new OrderServiceException(response.getMsg());

        }

    }
    private void reduceInventory(String productCode, int quantity) {
        // Make HTTP request to inventory service to reduce inventory
        webClientBuilder.build().put()
                .uri("http://inventory-service/api/v1/inventory/reduce")
                .bodyValue(Map.of("productCode", productCode, "quantity", quantity))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
    private Mono<? extends Throwable> handleError(ClientResponse response) {
        log.error("Client error received: {}", response.statusCode());
        return Mono.error(new InventoryServiceException("Error in inventory service"));
    }

    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest){
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }

    @Override
    public List<OrderCreateResponce> findAll() {
        return orderRepository.findAll().stream().map(this::mapToOrderCreateResponce).toList();
    }
    private OrderCreateResponce mapToOrderCreateResponce(Order source){
        OrderCreateResponce target = new OrderCreateResponce();
        BeanUtils.copyProperties(source, target);
        return target;

    }

    @Override
    public void deleteOrderById(Integer id) {
        orderRepository.deleteById(id);
    }  

    

    
}
