package com.sweetscoops;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetscoops.dto.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProducts_ReturnsAvailableProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void createProduct_RequiresAdminAuthentication() throws Exception {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Mint Delight");
        request.setDescription("Cooling mint flavour");
        request.setCategory("Ice Cream Cups");
        request.setPrice(new BigDecimal("90"));
        request.setImageUrl("https://example.com/mint.jpg");

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createProduct_AllowsAdmin() throws Exception {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Mint Delight");
        request.setDescription("Cooling mint flavour");
        request.setCategory("Ice Cream Cups");
        request.setPrice(new BigDecimal("90"));
        request.setImageUrl("https://example.com/mint.jpg");

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mint Delight"));
    }
}
