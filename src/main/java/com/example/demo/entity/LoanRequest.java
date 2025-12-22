package com.example.demo.entity;

import java.time.LocalDateTime;

public class LoanRequest {

    public enum Status { PENDING, APPROVED, REJECTED }

    private Long id;
    private User user;
    private Double requestedAmount;
    private Integer tenureMonths;
    private String status;
    private LocalDateTime submittedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Double getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(Double requestedAmount) { this.requestedAmount = requestedAmount; }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSubmittedAt() {
        if (submittedAt == null) submittedAt = LocalDateTime.now();
        return submittedAt;
    }
}
