package com.foodtruth.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Holds Anthropic API configuration read from application.yml or environment variables.
 * The API key is never exposed to the frontend — it stays server-side only.
 */
@Configuration
@ConfigurationProperties(prefix = "anthropic")
public class AnthropicConfig {

    private String apiKey;
    private String apiUrl = "https://api.anthropic.com/v1/messages";
    private String apiVersion = "2023-06-01";
    private String model = "claude-sonnet-4-20250514";
    private int maxTokens = 1800;

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getApiUrl() { return apiUrl; }
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }

    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getMaxTokens() { return maxTokens; }
    public void setMaxTokens(int maxTokens) { this.maxTokens = maxTokens; }

    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank();
    }
}
