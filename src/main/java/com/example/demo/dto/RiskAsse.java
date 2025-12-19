package com.example.demo.dto;

import java.time.LocalDateTime;

public class RiskAssessmentLogDto {
    private Long id;
    private Long loanRequestId;
    private Double dtiRatio;
    private String creditCheckStatus;
    private LocalDateTime timestamp;
    
    // Constructors
    public RiskAssessmentLogDto() {}
    
    public RiskAssessmentLogDto(Long id, Long loanRequestId, Double dtiRatio, 
                               String creditCheckStatus, LocalDateTime timestamp) {
        this.id = id;
        this.loanRequestId = loanRequestId;
        this.dtiRatio = dtiRatio;
        this.creditCheckStatus = creditCheckStatus;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getLoanRequestId() { return loanRequestId; }
    public void setLoanRequestId(Long loanRequestId) { this.loanRequestId = loanRequestId; }
    
    public Double getDtiRatio() { return dtiRatio; }
    public void setDtiRatio(Double dtiRatio) { this.dtiRatio = dtiRatio; }
    
    public String getCreditCheckStatus() { return creditCheckStatus; }
    public void setCreditCheckStatus(String creditCheckStatus) { this.creditCheckStatus = creditCheckStatus; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}