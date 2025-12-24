package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class FinancialProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private double income;
    private double existingEmi;
    private int creditScore;

    public FinancialProfile() {}

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public double getIncome() { return income; }
    public double getExistingEmi() { return existingEmi; }
    public int getCreditScore() { return creditScore; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setIncome(double income) { this.income = income; }
    public void setExistingEmi(double existingEmi) { this.existingEmi = existingEmi; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }
}
