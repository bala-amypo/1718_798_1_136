package com.example.demo.entity;

public class RiskAssessment {

    private Long id;
    private Double riskScore;
    private Double dtiRatio;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getRiskScore() {
        if (riskScore == null) return 0.0;
        return riskScore;
    }

    public Double getDtiRatio() {
        if (dtiRatio == null) return 0.0;
        return dtiRatio;
    }
}
