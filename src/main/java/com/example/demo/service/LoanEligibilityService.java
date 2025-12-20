package com.example.demo.service;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.entity.RiskAssessmentLog;
import java.util.List;

public interface LoanEligibilityService {
    EligibilityResult evaluateEligibility(Long loanRequestId);
    EligibilityResult getResultByRequest(Long requestId);
}

public interface RiskAssessmentLogService {
    RiskAssessmentLog logAssessment(RiskAssessmentLog log);
    List<RiskAssessmentLog> getLogsByRequest(Long requestId);
}