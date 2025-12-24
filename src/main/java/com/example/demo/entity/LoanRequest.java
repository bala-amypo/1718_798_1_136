package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_requests")
@Data
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double requestedAmount;

    @Column(nullable = false)
    private Integer tenureMonths;

    @Column(nullable = false)
    private String status = Status.PENDING.name();

    @CreationTimestamp
    private LocalDateTime submittedAt;

    public enum Status {
        PENDING, APPROVED, REJECTED, PROCESSING
    }
}