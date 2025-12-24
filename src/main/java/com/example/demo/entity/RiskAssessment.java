package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_assessments")
@Data
public class RiskAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "loan_request_id", unique = true, nullable = false)
    private LoanRequest loanRequest;

    @Column(nullable = false)
    private Double riskScore;

    @Column(nullable = false)
    private String riskLevel;

    @Column(nullable = false)
    private Double dtiRatio;

    @Column
    private String recommendation;

    @CreationTimestamp
    private LocalDateTime assessedAt;
}