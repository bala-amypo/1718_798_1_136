package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentService;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    @Autowired
    private RiskAssessmentLogRepository repo;

    @Override
    public RiskAssessmentLog assessRisk(FinancialProfile profile, LoanRequest request) {

        double riskScore = (profile.getExistingLoanEmi() / profile.getMonthlyIncome()) * 100;

        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setUserId(profile.getUserId());
        log.setRiskScore(riskScore);
        log.setMessage("Risk calculated");

        return repo.save(log);
    }
}
