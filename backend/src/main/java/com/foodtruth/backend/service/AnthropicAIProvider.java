package com.foodtruth.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtruth.backend.config.AnthropicConfig;
import com.foodtruth.backend.dto.AnalyzeRequest;
import com.foodtruth.backend.dto.AnalyzeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Anthropic implementation of {@link AIProvider}.
 * Calls the Anthropic Messages API with the FoodTruth prompt structure.
 * The API key is read from server-side configuration and never sent to the client.
 * Bean creation is controlled by {@link AIProviderConfig} based on the ai.provider property.
 */
public class AnthropicAIProvider implements AIProvider {

    private static final Logger log = LoggerFactory.getLogger(AnthropicAIProvider.class);

    private final AnthropicConfig anthropicConfig;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public AnthropicAIProvider(AnthropicConfig anthropicConfig,
                               RestClient restClient,
                               ObjectMapper objectMapper) {
        this.anthropicConfig = anthropicConfig;
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean isConfigured() {
        return anthropicConfig.isConfigured();
    }

    @Override
    public AnalyzeResponse analyze(AnalyzeRequest request) {
        if (!anthropicConfig.isConfigured()) {
            throw new IllegalStateException(
                "Anthropic API key is not configured. Set the ANTHROPIC_API_KEY environment variable.");
        }

        String prompt = buildPrompt(request);
        Map<String, Object> requestBody = buildRequestBody(request, prompt);

        String rawResponse;
        try {
            rawResponse = restClient.post()
                    .uri(anthropicConfig.getApiUrl())
                    .header("x-api-key", anthropicConfig.getApiKey())
                    .header("anthropic-version", anthropicConfig.getApiVersion())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);
        } catch (RestClientException e) {
            log.error("Anthropic API call failed", e);
            throw new RuntimeException("Failed to reach the AI analysis service. Please try again.", e);
        }

        return parseResponse(rawResponse);
    }

    private String buildPrompt(AnalyzeRequest request) {
        AnalyzeRequest.HealthProfile p = request.getProfile();
        String profileStr;
        if (p != null && (p.getAge() != null || p.getGoal() != null || p.getAllergies() != null)) {
            String allergies = p.getAllergies() != null ? String.join(", ", p.getAllergies()) : "none";
            profileStr = String.format(
                "User profile: Age %s, Gender %s, Weight %skg, Goal: %s, Conditions/Allergies: %s.",
                p.getAge() != null ? p.getAge() : "unknown",
                p.getGender() != null ? p.getGender() : "unknown",
                p.getWeight() != null ? p.getWeight() : "unknown",
                p.getGoal() != null ? p.getGoal() : "none",
                allergies
            );
        } else {
            profileStr = "No user profile.";
        }

        return """
            You are FoodTruth — brutally honest food health analyst. %s

            Respond ONLY with valid JSON, no markdown, no backticks, no extra text.

            {
              "food_name": "string",
              "health_score": 5,
              "score_color": "orange",
              "verdict": "string",
              "summary": "One sentence honest summary",
              "score_breakdown": [
                {"icon": "🍬", "label": "High sugar content", "impact": -2.5, "type": "negative"},
                {"icon": "🎨", "label": "Artificial colours", "impact": -1.0, "type": "negative"},
                {"icon": "🌾", "label": "Some dietary fibre", "impact": +0.5, "type": "positive"},
                {"icon": "⚗️", "label": "Preservatives present", "impact": -1.0, "type": "negative"},
                {"icon": "🧂", "label": "High sodium", "impact": -1.0, "type": "negative"}
              ],
              "personalized_insight": "string — specific to user profile, or general tip if no profile",
              "nutrients": [
                {"name": "Sugar", "level": 85, "status": "danger"},
                {"name": "Protein", "level": 20, "status": "ok"},
                {"name": "Fibre", "level": 10, "status": "warning"},
                {"name": "Fat", "level": 45, "status": "warning"},
                {"name": "Sodium", "level": 60, "status": "warning"}
              ],
              "ingredients": [{"name": "string", "risk": "danger"}],
              "harmful_effects": [{"icon": "🩸", "title": "string", "description": "string"}],
              "misleading_claims": [{"claim": "string", "truth": "string"}],
              "alternatives": [
                {"emoji": "🍜", "brand": "Patanjali", "name": "Patanjali Atta Noodles", "reason": "Made with whole wheat atta, lower maida content, no MSG", "where": "Available at Patanjali stores and most kirana shops", "score": 6},
                {"emoji": "🌾", "brand": "Bambino", "name": "Bambino Vermicelli", "reason": "Semolina-based, no artificial flavours, cleaner ingredients", "where": "Available at D-Mart, Big Bazaar, grocery stores", "score": 7},
                {"emoji": "🥣", "brand": "Homemade", "name": "Homemade Poha or Upma", "reason": "Zero preservatives, zero additives, full control over ingredients", "where": "Make at home in 10 minutes", "score": 9}
              ],
              "recommendations": ["string"]
            }

            Rules:
            - health_score: 1-10 integer, brutally honest
            - score_breakdown: exactly 4-6 items. impact is a decimal like +1.5 or -2.0. All impacts must sum to roughly (health_score - 10) to show how we arrived at the score from a baseline of 10. Show the math honestly.
            - score_color: red ≤3, orange 4-6, green ≥7
            - nutrients: 5 items, level 0-100
            - ingredients: 5-8 items
            - harmful_effects: 3-5 real effects
            - misleading_claims: at least 1
            - alternatives: exactly 3 REAL alternatives. CRITICAL RULES for alternatives:
              * Always suggest REAL brand names available in India (e.g. Patanjali, Saffola, Yoga Bar, RiteBite, Monsoon Harvest, Slurrp Farm, True Elements, Millet Amma, B Natural, Paper Boat, etc.)
              * Each must have: brand name, full product name, specific reason it is healthier (name the actual better/missing ingredients), where to buy it in India (D-Mart, Big Bazaar, Amazon, Blinkit, Swiggy Instamart, etc.), and a health score
              * NEVER suggest generic things like "eat a salad" or "homemade food" as the only options — at least 2 of 3 must be real purchasable branded products
              * The alternatives must be realistic substitutes — same meal occasion, similar taste profile
            - recommendations: 3-4 tips
            - personalized_insight: if allergies/conditions in profile match dangerous ingredients, FLAG IT clearly
            """.formatted(profileStr);
    }

    private Map<String, Object> buildRequestBody(AnalyzeRequest request, String prompt) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", anthropicConfig.getModel());
        body.put("max_tokens", anthropicConfig.getMaxTokens());

        if (request.hasImage()) {
            List<Map<String, Object>> content = new ArrayList<>();
            Map<String, Object> imageBlock = new HashMap<>();
            imageBlock.put("type", "image");
            Map<String, Object> source = new HashMap<>();
            source.put("type", "base64");
            source.put("media_type", request.getImageMediaType() != null ? request.getImageMediaType() : "image/jpeg");
            source.put("data", request.getImageBase64());
            imageBlock.put("source", source);
            content.add(imageBlock);

            Map<String, Object> textBlock = new HashMap<>();
            textBlock.put("type", "text");
            textBlock.put("text", prompt);
            content.add(textBlock);

            body.put("messages", List.of(Map.of("role", "user", "content", content)));
        } else {
            String userText = "Food item: \"" + request.getFoodName() + "\"\n\n" + prompt;
            body.put("messages", List.of(Map.of("role", "user", "content", userText)));
        }

        return body;
    }

    private AnalyzeResponse parseResponse(String rawResponse) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            JsonNode contentArray = root.path("content");
            if (contentArray.isMissingNode() || !contentArray.isArray() || contentArray.isEmpty()) {
                throw new RuntimeException("AI returned an empty response.");
            }

            StringBuilder textBuilder = new StringBuilder();
            for (JsonNode item : contentArray) {
                if ("text".equals(item.path("type").asText())) {
                    textBuilder.append(item.path("text").asText());
                }
            }

            String text = textBuilder.toString().trim();
            text = text.replaceAll("```json", "").replaceAll("```", "").trim();

            return objectMapper.readValue(text, AnalyzeResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse AI response as JSON", e);
            throw new RuntimeException("The AI returned an invalid response. Please try again.", e);
        }
    }
}
