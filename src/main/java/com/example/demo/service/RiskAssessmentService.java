package com.example.demo.service;

import com.example.demo.entity.RiskAssessment;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;

public interface RiskAssessmentService {

    RiskAssessment assessRisk(FinancialProfile profile, LoanRequest request);
}
