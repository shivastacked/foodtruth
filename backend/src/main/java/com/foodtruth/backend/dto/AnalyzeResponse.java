package com.foodtruth.backend.dto;

import java.util.List;

/**
 * Response body for POST /api/analyze.
 * Mirrors the JSON schema the existing frontend expects from the Anthropic API.
 */
public class AnalyzeResponse {

    private String foodName;
    private int healthScore;
    private String scoreColor;
    private String verdict;
    private String summary;
    private List<ScoreBreakdownItem> scoreBreakdown;
    private String personalizedInsight;
    private List<Nutrient> nutrients;
    private List<Ingredient> ingredients;
    private List<HarmfulEffect> harmfulEffects;
    private List<MisleadingClaim> misleadingClaims;
    private List<Alternative> alternatives;
    private List<String> recommendations;

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public int getHealthScore() { return healthScore; }
    public void setHealthScore(int healthScore) { this.healthScore = healthScore; }

    public String getScoreColor() { return scoreColor; }
    public void setScoreColor(String scoreColor) { this.scoreColor = scoreColor; }

    public String getVerdict() { return verdict; }
    public void setVerdict(String verdict) { this.verdict = verdict; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public List<ScoreBreakdownItem> getScoreBreakdown() { return scoreBreakdown; }
    public void setScoreBreakdown(List<ScoreBreakdownItem> scoreBreakdown) { this.scoreBreakdown = scoreBreakdown; }

    public String getPersonalizedInsight() { return personalizedInsight; }
    public void setPersonalizedInsight(String personalizedInsight) { this.personalizedInsight = personalizedInsight; }

    public List<Nutrient> getNutrients() { return nutrients; }
    public void setNutrients(List<Nutrient> nutrients) { this.nutrients = nutrients; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    public List<HarmfulEffect> getHarmfulEffects() { return harmfulEffects; }
    public void setHarmfulEffects(List<HarmfulEffect> harmfulEffects) { this.harmfulEffects = harmfulEffects; }

    public List<MisleadingClaim> getMisleadingClaims() { return misleadingClaims; }
    public void setMisleadingClaims(List<MisleadingClaim> misleadingClaims) { this.misleadingClaims = misleadingClaims; }

    public List<Alternative> getAlternatives() { return alternatives; }
    public void setAlternatives(List<Alternative> alternatives) { this.alternatives = alternatives; }

    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }

    public static class ScoreBreakdownItem {
        private String icon;
        private String label;
        private double impact;
        private String type;

        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

        public double getImpact() { return impact; }
        public void setImpact(double impact) { this.impact = impact; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class Nutrient {
        private String name;
        private int level;
        private String status;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getLevel() { return level; }
        public void setLevel(int level) { this.level = level; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class Ingredient {
        private String name;
        private String risk;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getRisk() { return risk; }
        public void setRisk(String risk) { this.risk = risk; }
    }

    public static class HarmfulEffect {
        private String icon;
        private String title;
        private String description;

        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class MisleadingClaim {
        private String claim;
        private String truth;

        public String getClaim() { return claim; }
        public void setClaim(String claim) { this.claim = claim; }

        public String getTruth() { return truth; }
        public void setTruth(String truth) { this.truth = truth; }
    }

    public static class Alternative {
        private String emoji;
        private String brand;
        private String name;
        private String reason;
        private String where;
        private int score;

        public String getEmoji() { return emoji; }
        public void setEmoji(String emoji) { this.emoji = emoji; }

        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }

        public String getWhere() { return where; }
        public void setWhere(String where) { this.where = where; }

        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }
    }
}
