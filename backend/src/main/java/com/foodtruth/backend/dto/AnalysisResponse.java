
package com.foodtruth.backend.dto;

import com.foodtruth.backend.entity.ScoreFactor;

import java.time.LocalDateTime;
import java.util.List;

public class AnalysisResponse {

    private Long id;
    private Long productId;
    private Integer healthScore;
    private String scoreColor;
    private String verdict;
    private String summary;
    private String personalizedInsight;
    private LocalDateTime createdAt;
    private List<ScoreFactor> scoreFactors;

    public AnalysisResponse(
            Long id,
            Long productId,
            Integer healthScore,
            String scoreColor,
            String verdict,
            String summary,
            String personalizedInsight,
            LocalDateTime createdAt,
            List<ScoreFactor> scoreFactors
    ) {
        this.id = id;
        this.productId = productId;
        this.healthScore = healthScore;
        this.scoreColor = scoreColor;
        this.verdict = verdict;
        this.summary = summary;
        this.personalizedInsight = personalizedInsight;
        this.createdAt = createdAt;
        this.scoreFactors = scoreFactors;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getHealthScore() {
        return healthScore;
    }

    public String getScoreColor() {
        return scoreColor;
    }

    public String getVerdict() {
        return verdict;
    }

    public String getSummary() {
        return summary;
    }

    public String getPersonalizedInsight() {
        return personalizedInsight;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ScoreFactor> getScoreFactors() {
        return scoreFactors;
    }
}