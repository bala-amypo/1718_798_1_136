package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class FinancialProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private double monthlyIncome;
    private double existingLoanEmi;

    public FinancialProfile() {}

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getExistingLoanEmi() {
        return existingLoanEmi;
    }

    public void setExistingLoanEmi(double existingLoanEmi) {
        this.existingLoanEmi = existingLoanEmi;
    }
}
