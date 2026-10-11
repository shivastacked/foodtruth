package com.foodtruth.backend.repository;

import com.foodtruth.backend.entity.FoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FoodProductRepository extends JpaRepository<FoodProduct, Long> {
    Optional<FoodProduct> findByNameIgnoreCase(String name);
}