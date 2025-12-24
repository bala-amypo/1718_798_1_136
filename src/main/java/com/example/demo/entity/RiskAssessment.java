package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_assessment_log")
public class RiskAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private double riskScore;

    @Column(nullable = false)
    private String riskLevel;

    private String notes;

    // Default constructor
    public RiskAssessment() {}

    // Parameterized constructor
    public RiskAssessment(Long userId, double riskScore, String riskLevel, String notes) {
        this.userId = userId;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
