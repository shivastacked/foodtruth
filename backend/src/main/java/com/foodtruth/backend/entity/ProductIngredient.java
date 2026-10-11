package com.foodtruth.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"product_id", "ingredient_id"}
        )
    }
)
public class ProductIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FoodProduct product;

    @ManyToOne
    private Ingredient ingredient;

    public ProductIngredient() {
    }

    public ProductIngredient(FoodProduct product, Ingredient ingredient) {
        this.product = product;
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public FoodProduct getProduct() {
        return product;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setProduct(FoodProduct product) {
        this.product = product;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}