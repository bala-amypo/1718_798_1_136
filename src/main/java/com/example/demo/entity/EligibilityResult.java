package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "eligibility_result")
public class EligibilityResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private boolean eligible;

    private double maxEligibleAmount;

    private String message;

    public EligibilityResult() {}

    public EligibilityResult(Long userId, boolean eligible, double maxEligibleAmount, String message) {
        this.userId = userId;
        this.eligible = eligible;
        this.maxEligibleAmount = maxEligibleAmount;
        this.message = message;
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

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public double getMaxEligibleAmount() {
        return maxEligibleAmount;
    }

    public void setMaxEligibleAmount(double maxEligibleAmount) {
        this.maxEligibleAmount = maxEligibleAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
