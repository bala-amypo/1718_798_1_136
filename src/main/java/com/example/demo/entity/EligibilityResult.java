package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "eligibility_results")
public class EligibilityResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean eligible;

    private Double emi;
    private Double eligibleAmount;

    private String riskLevel;

    private String reason;

    @OneToOne
    @JoinColumn(name = "loan_request_id")
    private LoanRequest loanRequest;

    public EligibilityResult() {
    }

    public Long getId() {
        return id;
    }

    public Boolean getEligible() {
        return eligible;
    }

    public void setEligible(Boolean eligible) {
        this.eligible = eligible;
    }

    public Double getEmi() {
        return emi;
    }

    public void setEmi(Double emi) {
        this.emi = emi;
    }

    public Double getEligibleAmount() {
        return eligibleAmount;
    }

    public void setEligibleAmount(Double eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

    public void setLoanRequest(LoanRequest loanRequest) {
        this.loanRequest = loanRequest;
    }
}
