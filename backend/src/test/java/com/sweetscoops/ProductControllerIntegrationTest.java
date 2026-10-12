package com.sweetscoops;

import com.sweetscoops.dto.OrderItemRequest;
import com.sweetscoops.dto.OrderRequest;
import com.sweetscoops.entity.Product;
import com.sweetscoops.repository.CustomerOrderRepository;
import com.sweetscoops.repository.ProductRepository;
import com.sweetscoops.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerOrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, productRepository);
    }

    @Test
    void createOrder_UsesDatabasePriceNotFrontendSubmittedValue() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Belgian Chocolate");
        product.setDescription("Rich chocolate");
        product.setCategory("Ice Cream Cups");
        product.setPrice(new BigDecimal("120.00"));
        product.setImageUrl("https://example.com/choco.jpg");
        product.setAvailable(true);

        when(productRepository.findByIdAndAvailableTrue(1L)).thenReturn(Optional.of(product));

        OrderRequest request = new OrderRequest();
        request.setCustomerName("Aditi");
        request.setPhone("9999999999");
        request.setDeliveryAddress("Bengaluru");
        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(1L);
        item.setQuantity(2);
        request.setItems(List.of(item));

        var response = orderService.createOrder(request);

        assertEquals(new BigDecimal("240.00"), response.getTotalAmount());
    }
}
