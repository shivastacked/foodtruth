package com.foodtruth.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
private List<ScoreFactor> scoreFactors = new ArrayList<>();

    @ManyToOne
    private FoodProduct product;

    private Integer healthScore;

    private String scoreColor;

    private String verdict;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String personalizedInsight;

    private LocalDateTime createdAt;

    public Analysis() {
    }

    public Analysis(
            FoodProduct product,
            Integer healthScore,
            String scoreColor,
            String verdict,
            String summary,
            String personalizedInsight
    ) {
        this.product = product;
        this.healthScore = healthScore;
        this.scoreColor = scoreColor;
        this.verdict = verdict;
        this.summary = summary;
        this.personalizedInsight = personalizedInsight;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public FoodProduct getProduct() {
        return product;
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

    public void setProduct(FoodProduct product) {
        this.product = product;
    }

    public void setHealthScore(Integer healthScore) {
        this.healthScore = healthScore;
    }

    public void setScoreColor(String scoreColor) {
        this.scoreColor = scoreColor;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPersonalizedInsight(String personalizedInsight) {
        this.personalizedInsight = personalizedInsight;
    }
    public List<ScoreFactor> getScoreFactors() {
    return scoreFactors;
}

public void setScoreFactors(List<ScoreFactor> scoreFactors) {
    this.scoreFactors = scoreFactors;
}
}