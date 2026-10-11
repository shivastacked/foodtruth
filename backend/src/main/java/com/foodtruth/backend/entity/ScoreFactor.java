package com.foodtruth.backend.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ScoreFactor {

    private String name;

    private Integer score;

    private String status;

    public ScoreFactor() {
    }

    public ScoreFactor(String name, Integer score, String status) {
        this.name = name;
        this.score = score;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
