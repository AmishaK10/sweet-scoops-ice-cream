package com.sweetscoops.service;

import com.sweetscoops.dto.CreateProductRequest;
import com.sweetscoops.dto.ProductResponse;
import com.sweetscoops.dto.UpdateProductRequest;
import com.sweetscoops.entity.Product;
import com.sweetscoops.exception.BadRequestException;
import com.sweetscoops.exception.ResourceNotFoundException;
import com.sweetscoops.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts(String category, String search) {
        String normalizedCategory = category == null || category.isBlank() ? null : category.trim();
        String normalizedSearch = search == null || search.isBlank() ? null : search.trim();

        List<Product> products = productRepository.findAvailableProducts(normalizedCategory, normalizedSearch);
        return products.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new BadRequestException("Category is required");
        }

        return productRepository.findByCategoryIgnoreCase(category.trim())
                .stream()
                .filter(Product::isAvailable)
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findByIdAndAvailableTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName().trim());
        product.setDescription(request.getDescription().trim());
        product.setCategory(request.getCategory().trim());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl().trim());
        product.setAvailable(request.isAvailable());

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (request.getName() != null) product.setName(request.getName().trim());
        if (request.getDescription() != null) product.setDescription(request.getDescription().trim());
        if (request.getCategory() != null) product.setCategory(request.getCategory().trim());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getImageUrl() != null) product.setImageUrl(request.getImageUrl().trim());
        if (request.getAvailable() != null) product.setAvailable(request.getAvailable());

        return toResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setAvailable(false);
        productRepository.save(product);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getImageUrl(),
                product.isAvailable()
        );
    }
}
