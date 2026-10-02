package com.foodtruth.backend.service;

import com.foodtruth.backend.dto.AnalyzeRequest;
import com.foodtruth.backend.dto.AnalyzeResponse;

/**
 * Abstraction for the AI provider capability FoodTruth needs.
 * The service layer depends on this interface rather than any specific
 * provider implementation, so providers (Anthropic, Gemini, OpenRouter, etc.)
 * can be swapped via configuration without touching business logic.
 */
public interface AIProvider {

    /**
     * Analyze a food product using the provider's AI model.
     *
     * @param request the analysis request (food name or image + optional profile)
     * @return the structured analysis result
     * @throws IllegalStateException if the provider is not configured (e.g. missing API key)
     * @throws RuntimeException      if the upstream AI call fails or returns an invalid response
     */
    AnalyzeResponse analyze(AnalyzeRequest request);

    /**
     * @return true if this provider has the configuration it needs to function
     *         (API key set, endpoint reachable, etc.)
     */
    boolean isConfigured();
}
