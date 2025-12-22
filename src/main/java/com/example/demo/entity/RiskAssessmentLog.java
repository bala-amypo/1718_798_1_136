package com.example.demo.entity;

import java.time.LocalDateTime;

public class RiskAssessmentLog {

    private Long id;
    private Long loanRequestId;
    private Double riskScore;
    private String riskLevel;
    private LocalDateTime createdAt;

    public RiskAssessmentLog() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLoanRequestId() { return loanRequestId; }
    public void setLoanRequestId(Long loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public Double getRiskScore() { return riskScore; }
    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
