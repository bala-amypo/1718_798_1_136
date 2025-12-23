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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {
    
    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final EligibilityResultRepository eligibilityResultRepository;
    
    @Autowired
    public LoanEligibilityServiceImpl(LoanRequestRepository loanRequestRepository, FinancialProfileRepository financialProfileRepository,
     EligibilityResultRepository eligibilityResultRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.eligibilityResultRepository = eligibilityResultRepository;
    }
    
    @Override
    public EligibilityResult evaluateEligibility(Long loanRequestId) {
        if (eligibilityResultRepository.findByLoanRequestId(loanRequestId).isPresent()) {
            throw new BadRequestException("Eligibility already evaluated");
        }
        
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found"));
        
        FinancialProfile profile = financialProfileRepository.findByUserId(loanRequest.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found"));
        
        Double totalMonthlyObligations = profile.getMonthlyExpenses() + 
                (profile.getExistingLoanEmi() != null ? profile.getExistingLoanEmi() : 0.0);
        Double dtiRatio = totalMonthlyObligations / profile.getMonthlyIncome();
        
        boolean isEligible = true;
        String rejectionReason = null;
        String riskLevel = "LOW";
        
        if (profile.getCreditScore() < 600) {
            isEligible = false;
            rejectionReason = "Credit score too low";
        } else if (dtiRatio > 0.5) {
            isEligible = false;
            rejectionReason = "Debt-to-income ratio too high";
        } else if (dtiRatio > 0.4) {
            riskLevel = "HIGH";
        } else if (dtiRatio > 0.3) {
            riskLevel = "MEDIUM";
        }
        
        Double maxEligibleAmount = isEligible ? 
                Math.min(loanRequest.getRequestedAmount(), profile.getMonthlyIncome() * 12) : 0.0;
        
        Double estimatedEmi = isEligible ? 
                maxEligibleAmount / loanRequest.getTenureMonths() : 0.0;
        
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