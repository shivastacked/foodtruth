package com.foodtruth.backend.repository;
import java.util.List;

import com.foodtruth.backend.entity.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductIngredientRepository
        extends JpaRepository<ProductIngredient, Long> {
                boolean existsByProductIdAndIngredientId(Long productId, Long ingredientId);
                List<ProductIngredient> findByProductId(Long productId);
}