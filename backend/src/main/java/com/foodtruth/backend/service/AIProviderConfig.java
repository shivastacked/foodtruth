package com.foodtruth.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtruth.backend.config.AnthropicConfig;
import com.foodtruth.backend.config.GeminiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Registers exactly one {@link AIProvider} bean based on the ai.provider
 * configuration property. Only the configured provider is instantiated;
 * the other implementation is not created at all.
 *
 * <p>Supported values: {@code anthropic}, {@code gemini}.
 * Any other value causes startup to fail with a clear error message.
 */
@Configuration
public class AIProviderConfig {

    private static final Logger log = LoggerFactory.getLogger(AIProviderConfig.class);

    @Bean
    public AIProvider aiProvider(
            @Value("${ai.provider:anthropic}") String providerName,
            AnthropicConfig anthropicConfig,
            GeminiConfig geminiConfig,
            RestClient restClient,
            ObjectMapper objectMapper) {

        log.info("Selecting AI provider: {}", providerName);

        return switch (providerName.toLowerCase()) {
            case "anthropic" -> new AnthropicAIProvider(anthropicConfig, restClient, objectMapper);
            case "gemini" -> new GeminiAIProvider(geminiConfig, restClient, objectMapper);
            default -> throw new IllegalArgumentException(
                "Unsupported ai.provider value: '" + providerName
                + "'. Supported values: 'anthropic', 'gemini'.");
        };
    }
}
