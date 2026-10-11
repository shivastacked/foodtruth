package com.foodtruth.backend.dto;

public class ProductRequest {

    private String name;
    private String brand;

    public ProductRequest() {
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}