package com.foodtruth.backend.exception;

public class FoodProductNotFoundException extends RuntimeException {

    public FoodProductNotFoundException(String message) {
        super(message);
    }
}
