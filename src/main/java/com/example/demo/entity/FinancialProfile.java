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
    
    @Column(nullable = false)
    private Double monthlyIncome;
    
    @Column(nullable = false)
    private Double monthlyExpenses;
    
    private Double existingLoanEmi;
    
    @Column(nullable = false)
    private Integer creditScore;
    
    @Column(nullable = false)
    private Double savingsBalance;
    
    private LocalDateTime lastUpdatedAt;
    
    public FinancialProfile() {
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    public FinancialProfile(User user, Double monthlyIncome, Double monthlyExpenses, 
                           Integer creditScore, Double savingsBalance) {
        this.user = user;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.creditScore = creditScore;
        this.savingsBalance = savingsBalance;
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(Double monthlyIncome) { 
        this.monthlyIncome = monthlyIncome; 
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    public Double getMonthlyExpenses() { return monthlyExpenses; }
    public void setMonthlyExpenses(Double monthlyExpenses) { 
        this.monthlyExpenses = monthlyExpenses;
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    public Double getExistingLoanEmi() { return existingLoanEmi; }
    public void setExistingLoanEmi(Double existingLoanEmi) { 
        this.existingLoanEmi = existingLoanEmi;
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    public Integer getCreditScore() { return creditScore; }
    public void setCreditScore(Integer creditScore) { 
        this.creditScore = creditScore;
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    public Double getSavingsBalance() { return savingsBalance; }
    public void setSavingsBalance(Double savingsBalance) { 
        this.savingsBalance = savingsBalance;
        this.lastUpdatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}