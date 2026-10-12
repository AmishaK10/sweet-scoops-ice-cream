package com.sweetscoops.config;

import com.sweetscoops.entity.Product;
import com.sweetscoops.repository.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) {
            return;
        }

        List<Product> products = List.of(
            new Product("Classic Vanilla", "Smooth vanilla bean ice cream with a velvety finish and a nostalgic creamy taste.", "Ice Cream Cones", new BigDecimal("80"), "https://images.unsplash.com/photo-1570197788417-0e823ef4b963?auto=format&fit=crop&w=900&q=80", true),
            new Product("Belgian Chocolate", "Rich, decadent chocolate ice cream blended with deep cocoa notes and a silky texture.", "Ice Cream Cups", new BigDecimal("120"), "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=900&q=80", true),
            new Product("Fresh Strawberry", "Juicy strawberry swirls with a bright berry finish and a refreshing creamy base.", "Ice Cream Cones", new BigDecimal("100"), "https://images.unsplash.com/photo-1551024601-bec78aea704b?auto=format&fit=crop&w=900&q=80", true),
            new Product("Alphonso Mango", "King of mangoes in a creamy scoop, delivering tropical sweetness and a chilled finish.", "Ice Cream Cups", new BigDecimal("110"), "https://images.unsplash.com/photo-1551024601-bec78aea704b?auto=format&fit=crop&w=900&q=80", true),
            new Product("Butterscotch Crunch", "Golden butterscotch ribbons and crunchy praline pieces for a caramel-like treat.", "Sundaes", new BigDecimal("100"), "https://images.unsplash.com/photo-1464306076886-da185f6a9d8f?auto=format&fit=crop&w=900&q=80", true),
            new Product("Cookies and Cream", "Creamy vanilla ice cream with cookie chunks in every scoop.", "Milkshakes", new BigDecimal("130"), "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?auto=format&fit=crop&w=900&q=80", true),
            new Product("Blueberry Cheesecake", "A dense yet creamy frozen delight with berry swirls and cheesecake undertones.", "Special Desserts", new BigDecimal("150"), "https://images.unsplash.com/photo-1551024601-bec78aea704b?auto=format&fit=crop&w=900&q=80", true),
            new Product("Chocolate Brownie Sundae", "Warm brownie chunks and rich chocolate fudge layered over silky ice cream.", "Sundaes", new BigDecimal("180"), "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=900&q=80", true),
            new Product("Chocolate Milkshake", "A thick, dreamy milkshake blended with smooth chocolate and whipped cream.", "Milkshakes", new BigDecimal("140"), "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=900&q=80", true),
            new Product("Family Ice Cream Tub", "A generous family-size tub with our most-loved flavours for sharing moments.", "Family Packs", new BigDecimal("350"), "https://images.unsplash.com/photo-1570197788417-0e823ef4b963?auto=format&fit=crop&w=900&q=80", true)
        );

        productRepository.saveAll(products);
    }
}
