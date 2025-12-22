package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class EligibilityResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean eligible;
    private Double eligibleAmount;
    private String riskLevel;

    @OneToOne
    private LoanRequest loanRequest;

    public EligibilityResult() {}

    public Long getId() { return id; }
    public Boolean getEligible() { return eligible; }
    public Double getEligibleAmount() { return eligibleAmount; }
    public String getRiskLevel() { return riskLevel; }

    public void setId(Long id) { this.id = id; }
    public void setEligible(Boolean eligible) { this.eligible = eligible; }
    public void setEligibleAmount(Double eligibleAmount) { this.eligibleAmount = eligibleAmount; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public void setLoanRequest(LoanRequest loanRequest) { this.loanRequest = loanRequest; }
}
