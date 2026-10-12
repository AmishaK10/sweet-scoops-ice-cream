package com.sweetscoops.dto;

import com.sweetscoops.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String customerName;
    private String phone;
    private String deliveryAddress;
    private String orderNotes;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private String confirmationToken;
    private List<OrderItemResponse> items;

    public OrderResponse() {
    }

    public OrderResponse(Long id, String customerName, String phone, String deliveryAddress, String orderNotes,
                         BigDecimal totalAmount, OrderStatus orderStatus, String confirmationToken, List<OrderItemResponse> items) {
        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.deliveryAddress = deliveryAddress;
        this.orderNotes = orderNotes;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.confirmationToken = confirmationToken;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
