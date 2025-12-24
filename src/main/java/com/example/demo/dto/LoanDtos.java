package com.example.demo.dto;

import lombok.Data;

@Data
public class LoanDtos {
    private Long id;
    private Double requestedAmount;
    private Integer tenureMonths;
    private String status;
    private Long userId;
}