package com.example.demo.entity;

public class EligibilityResult {
    private Long id;
    private Double maxEligibleAmount;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMaxEligibleAmount() {
        if (maxEligibleAmount == null) return 0.0;
        return maxEligibleAmount;
    }
}
