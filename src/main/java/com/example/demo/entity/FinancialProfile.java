// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.sql.Timestamp;

// @Entity
// public class FinancialProfile {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @OneToOne
//     @JoinColumn(name = "user_id", nullable = false)
//     private User user;

//     private Double monthlyIncome;
//     private Double monthlyExpenses;
//     private Double existingLoanEmi;
//     private Integer creditScore;
//     private Double savingsBalance;

//     private Timestamp lastUpdatedAt;

//     public FinancialProfile() {
//     }

//     public FinancialProfile(User user, Double monthlyIncome) {
//         this.user = user;
//         this.monthlyIncome = monthlyIncome;
//         this.lastUpdatedAt = new Timestamp(System.currentTimeMillis());
//     }

//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public User getUser() { return user; }
//     public void setUser(User user) { this.user = user; }

//     public Double getMonthlyIncome() { return monthlyIncome; }
//     public void setMonthlyIncome(Double monthlyIncome) { this.monthlyIncome = monthlyIncome; }

//     public Double getMonthlyExpenses() { return monthlyExpenses; }
//     public void setMonthlyExpenses(Double monthlyExpenses) { this.monthlyExpenses = monthlyExpenses; }

//     public Double getExistingLoanEmi() { return existingLoanEmi; }
//     public void setExistingLoanEmi(Double existingLoanEmi) { this.existingLoanEmi = existingLoanEmi; }

//     public Integer getCreditScore() { return creditScore; }
//     public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }

//     public Double getSavingsBalance() { return savingsBalance; }
//     public void setSavingsBalance(Double savingsBalance) { this.savingsBalance = savingsBalance; }

//     public Timestamp getLastUpdatedAt() { return lastUpdatedAt; }
//     public void setLastUpdatedAt(Timestamp lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
// }
