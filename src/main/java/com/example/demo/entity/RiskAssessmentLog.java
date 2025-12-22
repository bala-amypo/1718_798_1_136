package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_assessment_log")
public class RiskAssessmentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double amount;

    private String riskLevel;

    public RiskAssessmentLog() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
