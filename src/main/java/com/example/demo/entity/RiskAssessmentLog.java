package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_assessment_logs")
public class RiskAssessmentLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "loan_request_id", nullable = false)
    private Long loanRequestId;
    
    @Column(name = "dti_ratio")
    private Double dtiRatio;
    
    @Column(name = "credit_check_status")
    private String creditCheckStatus;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    public RiskAssessmentLog() {}
    
    // Getters and setters
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