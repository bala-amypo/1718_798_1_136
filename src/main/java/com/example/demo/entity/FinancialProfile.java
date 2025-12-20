package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_profiles")
public class FinancialProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "monthly_income", nullable = false)
    private Double monthlyIncome;
    
    @Column(name = "monthly_expenses", nullable = false)
    private Double monthlyExpenses;
    
    @Column(name = "existing_loan_emi")
    private Double existingLoanEmi;
    
    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;
    
    @Column(name = "savings_balance")
    private Double savingsBalance;
    
    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;
    
    public FinancialProfile() {}
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(Double monthlyIncome) { this.monthlyIncome = monthlyIncome; }
    public Double getMonthlyExpenses() { return monthlyExpenses; }
    public void setMonthlyExpenses(Double monthlyExpenses) { this.monthlyExpenses = monthlyExpenses; }
    public Double getExistingLoanEmi() { return existingLoanEmi; }
    public void setExistingLoanEmi(Double existingLoanEmi) { this.existingLoanEmi = existingLoanEmi; }
    public Integer getCreditScore() { return creditScore; }
    public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }
    public Double getSavingsBalance() { return savingsBalance; }
    public void setSavingsBalance(Double savingsBalance) { this.savingsBalance = savingsBalance; }
    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}