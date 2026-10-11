package com.foodtruth.backend.service;

import com.foodtruth.backend.dto.AnalyzeResponse;
import org.springframework.stereotype.Component;

@Component
public class AIAnalysisValidator {

    public void validate(AnalyzeResponse response) {

        if (response == null) {
            throw new IllegalArgumentException(
                    "AI returned no analysis"
            );
        }

        if (response.getFoodName() == null
                || response.getFoodName().isBlank()) {
            throw new IllegalArgumentException(
                    "AI did not identify the food product"
            );
        }

        if (response.getHealthScore() <0
                || response.getHealthScore() < 0
                || response.getHealthScore() > 100) {
            throw new IllegalArgumentException(
                    "AI returned an invalid health score"
            );
        }

        if (response.getVerdict() == null
                || response.getVerdict().isBlank()) {
            throw new IllegalArgumentException(
                    "AI returned no verdict"
            );
        }
    }
}