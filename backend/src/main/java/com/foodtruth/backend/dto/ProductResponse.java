package com.foodtruth.backend.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private String brand;

    public ProductResponse(Long id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }
}