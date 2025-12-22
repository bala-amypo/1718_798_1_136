package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.RiskAssessmentLog;

public interface RiskAssessmentRepository {
    Optional<RiskAssessment> findByLoanRequestId(Long id);
    RiskAssessment save(RiskAssessment ra);
}
