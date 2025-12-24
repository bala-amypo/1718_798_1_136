package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EligibilityResultRepository;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.service.LoanEligibilityService;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {
    
    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final EligibilityResultRepository eligibilityResultRepository;
    
    public EligibilityServiceImpl(LoanRequestRepository loanRequestRepository,
                                 FinancialProfileRepository financialProfileRepository,
                                 EligibilityResultRepository eligibilityResultRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.eligibilityResultRepository = eligibilityResultRepository;
    }
    
    @Override
    public EligibilityResult evaluateEligibility(Long loanRequestId) {
        // Check if already evaluated
        if (eligibilityResultRepository.findByLoanRequestId(loanRequestId).isPresent()) {
            throw new BadRequestException("Eligibility already evaluated");
        }
        
        // Get loan request
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan request not found"));
        
        // Get financial profile
        FinancialProfile profile = financialProfileRepository.findByUserId(loanRequest.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found"));
        
        // Calculate DTI ratio
        double totalMonthlyObligations = profile.getMonthlyExpenses() + profile.getExistingLoanEmi();
        double dtiRatio = (totalMonthlyObligations / profile.getMonthlyIncome()) * 100;
        
        // Calculate max eligible amount (simplified business logic)
        double maxEligibleAmount = calculateMaxEligibleAmount(profile, dtiRatio);
        
        // Calculate estimated EMI (simplified: 8% annual interest)
        double interestRate = 0.08 / 12; // Monthly interest rate
        double principal = Math.min(maxEligibleAmount, loanRequest.getRequestedAmount());
        double estimatedEmi = principal * interestRate * Math.pow(1 + interestRate, loanRequest.getTenureMonths()) 
                            / (Math.pow(1 + interestRate, loanRequest.getTenureMonths()) - 1);
        
        // Determine eligibility
        boolean isEligible = maxEligibleAmount >= 1000; // Minimum loan amount
        
        // Determine risk level
        String riskLevel = determineRiskLevel(profile.getCreditScore(), dtiRatio);
        
        // Create eligibility result
        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(loanRequest);
        result.setIsEligible(isEligible);
        result.setMaxEligibleAmount(maxEligibleAmount);
        result.setEstimatedEmi(estimatedEmi);
        result.setRiskLevel(EligibilityResult.RiskLevel.valueOf(riskLevel));
        
        if (!isEligible) {
            result.setRejectionReason("Insufficient eligibility based on income and credit score");
        }
        
        return eligibilityResultRepository.save(result);
    }
    
    private double calculateMaxEligibleAmount(FinancialProfile profile, double dtiRatio) {
        double baseAmount = profile.getMonthlyIncome() * 12; // 1 year income
        
        // Adjust based on credit score
        double creditScoreMultiplier = 1.0;
        if (profile.getCreditScore() >= 800) {
            creditScoreMultiplier = 1.5;
        } else if (profile.getCreditScore() >= 700) {
            creditScoreMultiplier = 1.2;
        } else if (profile.getCreditScore() >= 600) {
            creditScoreMultiplier = 1.0;
        } else {
            creditScoreMultiplier = 0.5;
        }
        
        // Adjust based on DTI
        double dtiMultiplier = 1.0;
        if (dtiRatio <= 30) {
            dtiMultiplier = 1.2;
        } else if (dtiRatio <= 50) {
            dtiMultiplier = 1.0;
        } else {
            dtiMultiplier = 0.5;
        }
        
        return baseAmount * creditScoreMultiplier * dtiMultiplier;
    }
    
    private String determineRiskLevel(int creditScore, double dtiRatio) {
        if (creditScore >= 750 && dtiRatio <= 35) {
            return "LOW";
        } else if (creditScore >= 650 && dtiRatio <= 50) {
            return "MEDIUM";
        } else {
            return "HIGH";
        }
    }
    
    @Override
    public EligibilityResult getByLoanRequestId(Long loanRequestId) {
        return eligibilityResultRepository.findByLoanRequestId(loanRequestId)
            .orElseThrow(() -> new ResourceNotFoundException("Eligibility result not found"));
    }
}