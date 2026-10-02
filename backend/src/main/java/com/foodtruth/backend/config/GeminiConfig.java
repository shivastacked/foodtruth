package com.foodtruth.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Holds Gemini API configuration read from application.yml or environment variables.
 * The API key is never exposed to the frontend — it stays server-side only.
 */
@Configuration
@ConfigurationProperties(prefix = "gemini")
public class GeminiConfig {

    private String apiKey;
    private String apiUrl = "https://generativelanguage.googleapis.com/v1beta";
    private String model = "gemini-2.0-flash";
    private int maxOutputTokens = 1800;
    private double temperature = 0.7;

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getApiUrl() { return apiUrl; }
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getMaxOutputTokens() { return maxOutputTokens; }
    public void setMaxOutputTokens(int maxOutputTokens) { this.maxOutputTokens = maxOutputTokens; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank();
    }
}
