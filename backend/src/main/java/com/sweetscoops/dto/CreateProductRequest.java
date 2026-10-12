package com.sweetscoops.repository;

import com.sweetscoops.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    Optional<CustomerOrder> findByIdAndConfirmationToken(Long id, String confirmationToken);
    List<CustomerOrder> findAllByOrderByCreatedAtDesc();
}
