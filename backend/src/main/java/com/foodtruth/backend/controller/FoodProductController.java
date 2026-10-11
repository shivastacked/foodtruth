package com.foodtruth.backend.controller;

import com.foodtruth.backend.dto.IngredientRequest;
import com.foodtruth.backend.dto.IngredientResponse;
import com.foodtruth.backend.dto.ProductRequest;
import com.foodtruth.backend.dto.ProductResponse;
import com.foodtruth.backend.service.FoodProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class FoodProductController {

    private final FoodProductService foodProductService;

    public FoodProductController(FoodProductService foodProductService) {
        this.foodProductService = foodProductService;
    }

    @PostMapping
public ProductResponse createProduct(@RequestBody ProductRequest request) {
    return foodProductService.createProduct(request);
}

   @GetMapping("/{id}")
public ProductResponse getProduct(@PathVariable Long id) {
    return foodProductService.getProductById(id);
}
@GetMapping
public List<ProductResponse> getAllProducts() {
    return foodProductService.getAllProducts();
}
@PutMapping("/{id}")
public ProductResponse updateProduct(
        @PathVariable Long id,
        @RequestBody ProductRequest request) {

    return foodProductService.updateProduct(id, request);
}
@DeleteMapping("/{id}")
public void deleteProduct(@PathVariable Long id) {
    foodProductService.deleteProduct(id);
}
@PostMapping("/{id}/ingredients")
public void addIngredient(
        @PathVariable Long id,
        @RequestBody IngredientRequest request) {

    foodProductService.addIngredient(id, request);
}
@GetMapping("/{id}/ingredients")
public List<IngredientResponse> getIngredients(@PathVariable Long id) {
    return foodProductService.getIngredients(id);
}
}
