package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private double loanAmount;
    private double income;
    private int duration;

    public LoanRequest() {}

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public double getLoanAmount() { return loanAmount; }
    public double getIncome() { return income; }
    public int getDuration() { return duration; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }
    public void setIncome(double income) { this.income = income; }
    public void setDuration(int duration) { this.duration = duration; }
}
