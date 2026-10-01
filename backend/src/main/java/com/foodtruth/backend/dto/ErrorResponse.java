package com.foodtruth.backend.dto;

import java.util.Map;

/**
 * Standard error response returned for all API failures.
 */
public class ErrorResponse {

    private final String error;
    private final String message;
    private final Map<String, String> validationErrors;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.validationErrors = null;
    }

    public ErrorResponse(String error, String message, Map<String, String> validationErrors) {
        this.error = error;
        this.message = message;
        this.validationErrors = validationErrors;
    }

    public String getError() { return error; }
    public String getMessage() { return message; }
    public Map<String, String> getValidationErrors() { return validationErrors; }
}
