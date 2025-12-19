package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_requests")
public class LoanRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "requested_amount", nullable = false)
    private Double requestedAmount;
    
    @Column(name = "tenure_months", nullable = false)
    private Integer tenureMonths;
    
    private String purpose;
    
    @Column(nullable = false)
    private String status = "PENDING";
    
    @Column(name = "applied_at")
    private LocalDateTime appliedAt;
    
    @OneToOne(mappedBy = "loanRequest")
    private EligibilityResult eligibilityResult;
    
    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
    }
    
    // Constructors
    public LoanRequest() {}
    
    public LoanRequest(User user, Double requestedAmount, Integer tenureMonths, String purpose) {
        this.user = user;
        this.requestedAmount = requestedAmount;
        this.tenureMonths = tenureMonths;
        this.purpose = purpose;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Double getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(Double requestedAmount) { this.requestedAmount = requestedAmount; }
    
    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }
    
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
    
    public EligibilityResult getEligibilityResult() { return eligibilityResult; }
    public void setEligibilityResult(EligibilityResult eligibilityResult) { this.eligibilityResult = eligibilityResult; }
}