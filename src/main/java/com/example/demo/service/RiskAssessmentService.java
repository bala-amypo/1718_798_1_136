package com.example.demo.service;

import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessmentLog;

public interface RiskAssessmentService {

    RiskAssessmentLog assessRisk(LoanRequest request);
}
