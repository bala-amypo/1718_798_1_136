package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessment;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.RiskAssessmentRepository;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;

    @Autowired
    public RiskAssessmentServiceImpl(LoanRequestRepository loanRequestRepository,
                                    FinancialProfileRepository financialProfileRepository,
                                    RiskAssessmentRepository riskAssessmentRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.riskAssessmentRepository = riskAssessmentRepository;
    }

    @Override
    public RiskAssessment assessRisk(Long loanRequestId) {
        // Check if already assessed
        if (riskAssessmentRepository.findByLoanRequestId(loanRequestId).isPresent()) {
            throw new BadRequestException("Risk already assessed for this loan request");
        }

        // Get loan request
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found"));

        // Get financial profile
        FinancialProfile financialProfile = financialProfileRepository.findByUserId(loanRequest.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found for user"));

        // Calculate risk
        RiskAssessment assessment = new RiskAssessment();
        assessment.setLoanRequest(loanRequest);
        assessment.setAssessedAt(LocalDateTime.now());

        // Calculate DTI ratio
        double monthlyIncome = financialProfile.getMonthlyIncome();
        double monthlyExpenses = financialProfile.getMonthlyExpenses();
        double existingEmi = financialProfile.getExistingLoanEmi();
        double requestedEmi = calculateEmi(loanRequest.getRequestedAmount(), loanRequest.getTenureMonths());
        
        double dtiRatio = 0.0;
        if (monthlyIncome > 0) {
            dtiRatio = (monthlyExpenses + existingEmi + requestedEmi) / monthlyIncome;
        }
        assessment.setDtiRatio(dtiRatio);

        // Calculate risk score (0-100, higher = riskier)
        double riskScore = 0.0;
        
        // Credit score component (30% weight)
        int creditScore = financialProfile.getCreditScore();
        double creditRisk = 100 - ((creditScore - 300) / 6.0); // Convert 300-900 to 0-100
        riskScore += creditRisk * 0.3;
        
        // DTI component (40% weight)
        double dtiRisk = Math.min(dtiRatio * 100, 100);
        riskScore += dtiRisk * 0.4;
        
        // Savings ratio component (20% weight)
        double savingsRatio = (financialProfile.getSavingsBalance() / loanRequest.getRequestedAmount());
        double savingsRisk = savingsRatio > 1 ? 0 : (1 - savingsRatio) * 100;
        riskScore += savingsRisk * 0.2;
        
        // Income stability component (10% weight) - simplified
        double incomeRisk = monthlyIncome < 5000 ? 50 : 0;
        riskScore += incomeRisk * 0.1;
        
        assessment.setRiskScore(Math.min(riskScore, 100.0));

        // Determine risk level
        String riskLevel;
        if (riskScore <= 30) {
            riskLevel = "LOW";
        } else if (riskScore <= 60) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "HIGH";
        }
        assessment.setRiskLevel(riskLevel);

        // Set recommendation
        if (riskScore <= 30) {
            assessment.setRecommendation("APPROVE - Low risk");
        } else if (riskScore <= 60) {
            assessment.setRecommendation("APPROVE WITH CONDITIONS - Medium risk");
        } else {
            assessment.setRecommendation("REJECT - High risk");
        }

        return riskAssessmentRepository.save(assessment);
    }

    @Override
    public RiskAssessment getByLoanRequestId(Long loanRequestId) {
        return riskAssessmentRepository.findByLoanRequestId(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Risk assessment not found"));
    }

    private double calculateEmi(double principal, int tenureMonths) {
        if (tenureMonths <= 0) return 0;
        double rate = 0.12 / 12; // 12% annual interest, monthly rate
        return principal * rate * Math.pow(1 + rate, tenureMonths) / 
               (Math.pow(1 + rate, tenureMonths) - 1);
    }
}