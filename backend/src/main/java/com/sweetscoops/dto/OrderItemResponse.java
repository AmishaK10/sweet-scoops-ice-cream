package com.sweetscoops.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

    @NotBlank(message = "Customer name is required")
    @Size(max = 150, message = "Customer name must be under 150 characters")
    private String customerName;

    @NotBlank(message = "Phone number is required")
    @Size(max = 50, message = "Phone number is too long")
    private String phone;

    @NotBlank(message = "Delivery address is required")
    @Size(max = 2000, message = "Delivery address is too long")
    private String deliveryAddress;

    @Size(max = 2000, message = "Order notes must be under 2000 characters")
    private String orderNotes;

    @NotNull(message = "At least one product must be included")
    @Valid
    private List<OrderItemRequest> items = new ArrayList<>();

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getOrderNotes() { return orderNotes; }
    public void setOrderNotes(String orderNotes) { this.orderNotes = orderNotes; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
