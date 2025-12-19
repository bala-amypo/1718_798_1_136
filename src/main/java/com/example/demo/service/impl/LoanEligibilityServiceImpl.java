package com.example.demo.service.impl;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
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
    
    public LoanEligibilityServiceImpl(LoanRequestRepository loanRequestRepository,
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
        
        // Calculate DTI
        Double totalMonthlyObligations = profile.getMonthlyExpenses() + 
                (profile.getExistingLoanEmi() != null ? profile.getExistingLoanEmi() : 0);
        Double dtiRatio = totalMonthlyObligations / profile.getMonthlyIncome();
        
        // Business rules
        Boolean isEligible = true;
        String riskLevel = "LOW";
        Double maxEligibleAmount = 0.0;
        String rejectionReason = null;
        
        // Rule 1: Credit Score
        if (profile.getCreditScore() < 600) {
            isEligible = false;
            rejectionReason = "Credit score below minimum threshold (600)";
        }
        // Rule 2: DTI Ratio
        else if (dtiRatio > 0.5) {
            isEligible = false;
            rejectionReason = "Debt-to-Income ratio too high";
        } else if (dtiRatio > 0.3) {
            riskLevel = "MEDIUM";
        }
        
        // Rule 3: Income to EMI ratio
        if (isEligible) {
            // Calculate max eligible amount (simplified formula)
            Double maxEmiAffordable = profile.getMonthlyIncome() * 0.4 - totalMonthlyObligations;
            if (maxEmiAffordable <= 0) {
                isEligible = false;
                rejectionReason = "Insufficient income after existing obligations";
            } else {
                // Simplified calculation: assuming 8% annual interest rate
                Double monthlyInterestRate = 0.08 / 12;
                Double loanTermMonths = loanRequest.getTenureMonths().doubleValue();
                
                // Present value of annuity formula
                Double annuityFactor = (1 - Math.pow(1 + monthlyInterestRate, -loanTermMonths)) / monthlyInterestRate;
                maxEligibleAmount = maxEmiAffordable * annuityFactor;
                
                // Cap at requested amount
                if (maxEligibleAmount > loanRequest.getRequestedAmount()) {
                    maxEligibleAmount = loanRequest.getRequestedAmount();
                }
                
                // Adjust risk based on credit score
                if (profile.getCreditScore() < 700) {
                    riskLevel = "MEDIUM";
                }
                if (profile.getCreditScore() < 650) {
                    riskLevel = "HIGH";
                }
                
                // Adjust risk based on DTI
                if (dtiRatio > 0.4) {
                    riskLevel = "HIGH";
                }
            }
        }
        
        // Calculate estimated EMI
        Double estimatedEmi = 0.0;
        if (maxEligibleAmount > 0) {
            Double monthlyInterestRate = 0.08 / 12;
            Double loanTermMonths = loanRequest.getTenureMonths().doubleValue();
            
            // EMI formula: [P x R x (1+R)^N]/[(1+R)^N-1]
            Double numerator = maxEligibleAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonths);
            Double denominator = Math.pow(1 + monthlyInterestRate, loanTermMonths) - 1;
            estimatedEmi = numerator / denominator;
        }
        
        // Create eligibility result
        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(loanRequest);
        result.setIsEligible(isEligible);
        result.setMaxEligibleAmount(maxEligibleAmount);
        result.setEstimatedEmi(estimatedEmi);
        result.setRiskLevel(riskLevel);
        result.setRejectionReason(rejectionReason);
        
        return eligibilityResultRepository.save(result);
    }
    
    @Override
    public EligibilityResult getByLoanRequestId(Long loanRequestId) {
        return eligibilityResultRepository.findByLoanRequestId(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Eligibility result not found"));
    }
}