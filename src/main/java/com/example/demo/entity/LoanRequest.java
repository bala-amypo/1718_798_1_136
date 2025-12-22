package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double requestedAmount;
    private Integer tenureMonths;
    private String status;

    @ManyToOne
    private User user;

    public LoanRequest() {}

    public Long getId() { return id; }
    public Double getRequestedAmount() { return requestedAmount; }
    public Integer getTenureMonths() { return tenureMonths; }
    public String getStatus() { return status; }
    public User getUser() { return user; }

    public void setId(Long id) { this.id = id; }
    public void setRequestedAmount(Double requestedAmount) { this.requestedAmount = requestedAmount; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }
    public void setStatus(String status) { this.status = status; }
    public void setUser(User user) { this.user = user; }
}
