package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class FinancialProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monthlyIncome;
    private Double monthlyExpenses;
    private Integer creditScore;

    @OneToOne
    private User user;

    public FinancialProfile() {}

    public Long getId() { return id; }
    public Double getMonthlyIncome() { return monthlyIncome; }
    public Double getMonthlyExpenses() { return monthlyExpenses; }
    public Integer getCreditScore() { return creditScore; }
    public User getUser() { return user; }

    public void setId(Long id) { this.id = id; }
    public void setMonthlyIncome(Double monthlyIncome) { this.monthlyIncome = monthlyIncome; }
    public void setMonthlyExpenses(Double monthlyExpenses) { this.monthlyExpenses = monthlyExpenses; }
    public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }
    public void setUser(User user) { this.user = user; }
}
