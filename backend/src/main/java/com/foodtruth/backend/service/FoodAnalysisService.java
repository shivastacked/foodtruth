package com.foodtruth.backend.service;

import com.foodtruth.backend.dto.AnalyzeRequest;
import com.foodtruth.backend.dto.AnalyzeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Business service for food analysis.
 * Depends on the {@link AIProvider} abstraction rather than any specific
 * provider implementation, so the AI backend can be swapped via configuration
 * without touching this layer.
 */
@Service
public class FoodAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(FoodAnalysisService.class);

    private final AIProvider aiProvider;

    public FoodAnalysisService(AIProvider aiProvider) {
        this.aiProvider = aiProvider;
    }

    public AnalyzeResponse analyze(AnalyzeRequest request) {
        if (!aiProvider.isConfigured()) {
            throw new IllegalStateException(
                "The configured AI provider is not ready. Set the appropriate API key environment variable.");
        }
        return aiProvider.analyze(request);
    }
}
