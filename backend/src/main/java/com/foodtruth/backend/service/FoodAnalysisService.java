package com.foodtruth.backend.service;

import com.foodtruth.backend.dto.AnalyzeRequest;
import com.foodtruth.backend.dto.AnalyzeResponse;
import com.foodtruth.backend.entity.Analysis;
import com.foodtruth.backend.entity.FoodProduct;
import com.foodtruth.backend.exception.FoodProductNotFoundException;
import com.foodtruth.backend.repository.AnalysisRepository;
import com.foodtruth.backend.repository.FoodProductRepository;


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
    private final FoodProductRepository foodProductRepository;
private final AnalysisRepository analysisRepository;
private final AIAnalysisValidator aiAnalysisValidator;

    public FoodAnalysisService(AIProvider aiProvider, FoodProductRepository foodProductRepository, AnalysisRepository analysisRepository, AIAnalysisValidator aiAnalysisValidator) {
        this.aiProvider = aiProvider;
        this.foodProductRepository = foodProductRepository;
        this.analysisRepository = analysisRepository;
        this.aiAnalysisValidator = aiAnalysisValidator;
    }

    public AnalyzeResponse analyze(AnalyzeRequest request) {

    if (!aiProvider.isConfigured()) {
        throw new IllegalStateException(
                "The configured AI provider is not ready. Set the appropriate API key environment variable."
        );
    }

    AnalyzeResponse response = aiProvider.analyze(request);

    aiAnalysisValidator.validate(response);

    FoodProduct product = foodProductRepository
            .findByNameIgnoreCase(response.getFoodName())
            .orElseThrow(() -> new FoodProductNotFoundException(
                    "Food product '" + response.getFoodName()
                            + "' is not registered"
            ));

    Analysis analysis = new Analysis(
            product,
            response.getHealthScore(),
            response.getScoreColor(),
            response.getVerdict(),
            response.getSummary(),
            response.getPersonalizedInsight()
    );

    analysisRepository.save(analysis);

    return response;
}
}
