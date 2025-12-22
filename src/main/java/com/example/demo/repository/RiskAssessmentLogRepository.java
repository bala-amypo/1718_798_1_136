package com.example.demo.repository;

import com.example.demo.entity.RiskAssessmentLog;
import java.util.Optional;

public interface RiskAssessmentLogRepository {

    Optional<RiskAssessmentLog> findByLoanRequestId(Long loanRequestId);

    RiskAssessmentLog save(RiskAssessmentLog log);
}
