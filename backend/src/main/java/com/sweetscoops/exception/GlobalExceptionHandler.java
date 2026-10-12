package com.sweetscoops.service;

import com.sweetscoops.dto.OrderItemRequest;
import com.sweetscoops.dto.OrderItemResponse;
import com.sweetscoops.dto.OrderRequest;
import com.sweetscoops.dto.OrderResponse;
import com.sweetscoops.entity.CustomerOrder;
import com.sweetscoops.entity.OrderItem;
import com.sweetscoops.entity.OrderStatus;
import com.sweetscoops.entity.Product;
import com.sweetscoops.exception.BadRequestException;
import com.sweetscoops.exception.ForbiddenOperationException;
import com.sweetscoops.exception.ResourceNotFoundException;
import com.sweetscoops.repository.CustomerOrderRepository;
import com.sweetscoops.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {

    private final CustomerOrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(CustomerOrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("At least one product must be included in the order");
        }

        String customerName = request.getCustomerName() == null ? "" : request.getCustomerName().trim();
        String phone = request.getPhone() == null ? "" : request.getPhone().trim();
        String address = request.getDeliveryAddress() == null ? "" : request.getDeliveryAddress().trim();

        if (customerName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            throw new BadRequestException("Customer details are incomplete");
        }

        if (request.getItems().size() > 20) {
            throw new BadRequestException("Order contains too many items");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            if (itemRequest == null || itemRequest.getProductId() == null || itemRequest.getQuantity() == null) {
                throw new BadRequestException("Each order item must include a product ID and quantity");
            }

            if (itemRequest.getQuantity() < 1 || itemRequest.getQuantity() > 20) {
                throw new BadRequestException("Quantity for product " + itemRequest.getProductId() + " must be between 1 and 20");
            }

            Product product = productRepository.findByIdAndAvailableTrue(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found or unavailable: " + itemRequest.getProductId()));

            BigDecimal unitPrice = product.getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            total = total.add(lineTotal);

            OrderItem orderItem = new OrderItem(
                    product,
                    product.getName(),
                    unitPrice,
                    itemRequest.getQuantity(),
                    lineTotal
            );
            orderItems.add(orderItem);
        }

        CustomerOrder order = new CustomerOrder(
                customerName,
                phone,
                address,
                request.getOrderNotes() == null ? null : request.getOrderNotes().trim(),
                total,
                OrderStatus.PLACED,
                UUID.randomUUID().toString()
        );

        for (OrderItem orderItem : orderItems) {
            order.addItem(orderItem);
        }

        CustomerOrder savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id, String confirmationToken) {
        CustomerOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (confirmationToken != null && !confirmationToken.isBlank()) {
            if (!Objects.equals(confirmationToken, order.getConfirmationToken())) {
                throw new ForbiddenOperationException("Invalid confirmation token for this order");
            }
            return mapToResponse(order);
        }

        if (order.getConfirmationToken() == null || order.getConfirmationToken().isBlank()) {
            return mapToResponse(order);
        }

        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream().map(this::mapToResponse).toList();
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatus nextStatus) {
        CustomerOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        validateStatusTransition(order.getOrderStatus(), nextStatus);
        order.setOrderStatus(nextStatus);
        return mapToResponse(orderRepository.save(order));
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus next) {
        if (current == null || next == null) {
            throw new BadRequestException("Both current and next order statuses are required");
        }

        if (current == next) {
            return;
        }

        boolean valid = switch (current) {
            case PLACED -> next == OrderStatus.CONFIRMED || next == OrderStatus.PREPARING || next == OrderStatus.CANCELLED;
            case CONFIRMED -> next == OrderStatus.PREPARING || next == OrderStatus.READY || next == OrderStatus.CANCELLED;
            case PREPARING -> next == OrderStatus.READY || next == OrderStatus.OUTFORDELIVERY || next == OrderStatus.CANCELLED;
            case READY -> next == OrderStatus.OUTFORDELIVERY || next == OrderStatus.CANCELLED;
            case OUTFORDELIVERY -> next == OrderStatus.DELIVERED || next == OrderStatus.CANCELLED;
            case DELIVERED, CANCELLED -> false;
        };

        if (!valid) {
            throw new ForbiddenOperationException("Invalid order status transition from " + current + " to " + next);
        }
    }

    private OrderResponse mapToResponse(CustomerOrder order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProductNameSnapshot(),
                        item.getQuantity(),
                        item.getUnitPriceSnapshot(),
                        item.getLineTotal()
                )).toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getPhone(),
                order.getDeliveryAddress(),
                order.getOrderNotes(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getConfirmationToken(),
                items
        );
    }
}
