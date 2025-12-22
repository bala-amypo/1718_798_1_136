package com.example.demo.service;

import com.example.demo.entity.RiskAssessmentLog;

public interface RiskAssessmentService {

    RiskAssessmentLog logRisk(Long userId, Double amount, String riskLevel);
}
