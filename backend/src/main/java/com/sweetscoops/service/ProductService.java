package com.sweetscoops.controller;

import com.sweetscoops.dto.OrderRequest;
import com.sweetscoops.dto.OrderResponse;
import com.sweetscoops.dto.OrderStatusUpdateRequest;
import com.sweetscoops.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable Long id,
            @RequestParam(required = false) String confirmationToken) {
        return ResponseEntity.ok(orderService.getOrderById(id, confirmationToken));
    }

    @GetMapping("/orders")
    public List<OrderResponse> getOrders() {
        return orderService.getOrders();
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, request.getStatus()));
    }
}
