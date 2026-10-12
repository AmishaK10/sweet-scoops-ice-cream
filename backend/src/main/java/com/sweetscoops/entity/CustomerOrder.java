package com.sweetscoops.repository;

import com.sweetscoops.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAvailableTrueOrderByNameAsc();

    @Query("SELECT p FROM Product p WHERE p.available = true AND (:category IS NULL OR p.category = :category) AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) ORDER BY p.name ASC")
    List<Product> findAvailableProducts(@Param("category") String category, @Param("search") String search);

    Page<Product> findByAvailableTrue(Pageable pageable);

    Page<Product> findByAvailableTrueAndCategoryIgnoreCase(String category, Pageable pageable);

    Page<Product> findByAvailableTrueAndNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Product> findByIdAndAvailableTrue(Long id);

    List<Product> findByCategoryIgnoreCase(String category);
}
