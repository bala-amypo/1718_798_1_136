package com.example.demo.service;

import com.example.demo.entity.RiskAssessmentLog;

public interface RiskAssessmentService {
    RiskAssessmentLog assessRisk(Long loanRequestId);
}
