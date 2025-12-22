package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class RiskAssessmentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double dtiRatio;
    private String status;
    private Long loanRequestId;

    public RiskAssessmentLog() {}

    public Long getId() { return id; }
    public Double getDtiRatio() { return dtiRatio; }
    public String getStatus() { return status; }
    public Long getLoanRequestId() { return loanRequestId; }

    public void setId(Long id) { this.id = id; }
    public void setDtiRatio(Double dtiRatio) { this.dtiRatio = dtiRatio; }
    public void setStatus(String status) { this.status = status; }
    public void setLoanRequestId(Long loanRequestId) { this.loanRequestId = loanRequestId; }
}
