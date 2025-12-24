package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessment;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.stereotype.Service;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    @Override
    public RiskAssessment assessRisk(FinancialProfile profile, LoanRequest request) {

        double income = profile.getMonthlyIncome();
        double expenses = profile.getMonthlyExpenses() + profile.getExistingLoanEmi();

        double dti = (income == 0) ? 0 : (expenses / income) * 100;

        double creditScore = profile.getCreditScore();
        double riskScore = Math.max(0, Math.min(100, 100 - (creditScore / 9)));

        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setRiskScore(riskScore);
        log.setDtiRatio(dti);
        log.setMessage("Risk evaluated");

        return log;
    }
}
