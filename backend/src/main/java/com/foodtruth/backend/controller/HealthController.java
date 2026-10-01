package com.foodtruth.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Root controller exposing a simple health-check endpoint.
 * Feature-specific controllers (analyze, profile, history, shame) will be
 * added here as they are implemented.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "FoodTruth Backend");
    }
}
