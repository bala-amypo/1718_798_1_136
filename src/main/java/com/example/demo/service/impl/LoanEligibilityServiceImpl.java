package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.LoanEligibilityService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {
    
    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final EligibilityResultRepository eligibilityResultRepository;
    private final RiskAssessmentLogRepository riskAssessmentLogRepository;
    
    public LoanEligibilityServiceImpl(
            LoanRequestRepository loanRequestRepository,
            FinancialProfileRepository financialProfileRepository,
            EligibilityResultRepository eligibilityResultRepository,
            RiskAssessmentLogRepository riskAssessmentLogRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.eligibilityResultRepository = eligibilityResultRepository;
        this.riskAssessmentLogRepository = riskAssessmentLogRepository;
    }
    
    @Override
    public EligibilityResult evaluateEligibility(Long loanRequestId) {
        // 1. Fetch loan request
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Loan request not found"));
        
        // 2. Fetch financial profile (must use findByUserld)
        FinancialProfile profile = financialProfileRepository.findByUserld(loanRequest.getUser().getId());
        if (profile == null) {
            throw new IllegalArgumentException("Financial profile not found");
        }
        
        // 3. Calculate DTI
        double totalMonthlyObligations = profile.getMonthlyExpenses() + profile.getExistingLoanEmi();
        double dtiRatio = (totalMonthlyObligations / profile.getMonthlyIncome()) * 100;
        
        // 4. Check eligibility criteria
        boolean isEligible = true;
        double maxEligibleAmount = 0.0;
        String riskLevel = "LOW";
        String rejectionReason = null;
        
        // Rule 1: DTI should be <= 40%
        if (dtiRatio > 40) {
            isEligible = false;
            riskLevel = "HIGH";
            rejectionReason = "DTI ratio too high: " + String.format("%.2f", dtiRatio) + "%";
        }
        
        // Rule 2: Credit score check
        else if (profile.getCreditScore() < 650) {
            isEligible = false;
            riskLevel = "MEDIUM";
            rejectionReason = "Credit score too low: " + profile.getCreditScore();
        }
        
        // Rule 3: Requested amount check
        else if (loanRequest.getRequestedAmount() > (profile.getMonthlyIncome() * 24)) {
            isEligible = false;
            riskLevel = "MEDIUM";
            rejectionReason = "Requested amount exceeds 24 months of income";
        }
        
        // If eligible, calculate max amount
        if (isEligible) {
            maxEligibleAmount = Math.min(
                profile.getMonthlyIncome() * 24, // 24 months income
                profile.getSavingsBalance() * 10 // 10 times savings
            );
            
            // Calculate estimated EMI
            double rate = 0.12 / 12; // 12% annual, monthly
            int tenure = loanRequest.getTenureMonths();
            double principal = loanRequest.getRequestedAmount();
            
            double emi = principal * rate * Math.pow(1 + rate, tenure) / 
                        (Math.pow(1 + rate, tenure) - 1);
            
            // Set risk level based on DTI
            if (dtiRatio > 30) {
                riskLevel = "MEDIUM";
            } else if (dtiRatio > 20) {
                riskLevel = "LOW";
            }
        }
        
        // 5. Create EligibilityResult
        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(loanRequest);
        result.setEligible(isEligible);
        result.setMaxEligibleAmount(maxEligibleAmount);
        result.setRiskLevel(riskLevel);
        result.setRejectionReason(rejectionReason);
        result.setCalculatedAt(LocalDateTime.now());
        
        // 6. Create RiskAssessmentLog
        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setLoanRequestId(loanRequestId);
        log.setDtiRatio(dtiRatio);
        log.setCreditCheckStatus(profile.getCreditScore() >= 650 ? "PASS" : "FAIL");
        log.setTimestamp(LocalDateTime.now());
        
        // 7. Save results
        eligibilityResultRepository.save(result);
        riskAssessmentLogRepository.save(log);
        
        return result;
    }
    
    @Override
    public EligibilityResult getResultByRequest(Long requestId) {
        return eligibilityResultRepository.findByLoanRequestid(requestId);
    }
}