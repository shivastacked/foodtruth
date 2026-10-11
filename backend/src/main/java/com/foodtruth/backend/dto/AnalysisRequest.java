package com.foodtruth.backend.dto;

import com.foodtruth.backend.entity.ScoreFactor;

import java.util.List;

public class AnalysisRequest {

    private Integer healthScore;
    private String scoreColor;
    private String verdict;
    private String summary;
    private String personalizedInsight;
    private List<ScoreFactor> scoreFactors;

    public AnalysisRequest() {
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

    public List<ScoreFactor> getScoreFactors() {
        return scoreFactors;
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

    public void setScoreFactors(List<ScoreFactor> scoreFactors) {
        this.scoreFactors = scoreFactors;
    }
}