package com.example.demo.entity;

public class EligibilityResult {

    private LoanRequest loanRequest;
    private boolean eligible;
    private Double eligibleAmount;
    private String riskLevel;

    public void setLoanRequest(LoanRequest loanRequest) {
        this.loanRequest = loanRequest;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public void setEligibleAmount(Double eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
