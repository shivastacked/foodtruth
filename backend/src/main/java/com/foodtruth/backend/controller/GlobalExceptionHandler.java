package com.foodtruth.backend.controller;

import com.foodtruth.backend.dto.ErrorResponse;
import com.foodtruth.backend.exception.AnalysisNotFoundException;
import com.foodtruth.backend.exception.FoodProductNotFoundException;
import com.foodtruth.backend.exception.IngredientAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that converts validation and unexpected errors
 * into consistent JSON error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("validation_error", "Request validation failed", errors));
    }
    @ExceptionHandler(IngredientAlreadyExistsException.class)
      public ResponseEntity<ErrorResponse> handleIngredientAlreadyExists(
        IngredientAlreadyExistsException ex) {

    ErrorResponse response = new ErrorResponse(
            "ingredient_already_exists",
            ex.getMessage(),
            null
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("internal_error", "An unexpected error occurred."));
    }
    @ExceptionHandler(FoodProductNotFoundException.class)
public ResponseEntity<ErrorResponse> handleFoodProductNotFound(
        FoodProductNotFoundException ex) {

    ErrorResponse response = new ErrorResponse(
            "product_not_found",
            ex.getMessage(),
            null
    );

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response);
}
@ExceptionHandler(AnalysisNotFoundException.class)
public ResponseEntity<ErrorResponse> handleAnalysisNotFound(
        AnalysisNotFoundException ex) {

    ErrorResponse response = new ErrorResponse(
            "analysis_not_found",
            ex.getMessage(),
            null
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
}
}
