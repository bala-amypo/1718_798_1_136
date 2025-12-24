package com.example.demo.service.impl;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EligibilityResultRepository;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.service.EligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class EligibilityServiceImpl implements EligibilityService {

    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final EligibilityResultRepository eligibilityResultRepository;

    @Autowired
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
            throw new BadRequestException("Eligibility already evaluated for this loan request");
        }

        // Get loan request
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found"));

        // Get financial profile
        FinancialProfile financialProfile = financialProfileRepository.findByUserId(loanRequest.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found for user"));

        // Calculate eligibility
        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(loanRequest);
        result.setCalculatedAt(LocalDateTime.now());

        // Business logic for eligibility calculation
        double monthlyIncome = financialProfile.getMonthlyIncome();
        double monthlyExpenses = financialProfile.getMonthlyExpenses();
        double existingEmi = financialProfile.getExistingLoanEmi();
        int creditScore = financialProfile.getCreditScore();
        double savings = financialProfile.getSavingsBalance();
        
        // DTI ratio calculation
        double dti = (monthlyExpenses + existingEmi) / monthlyIncome;
        
        // Determine eligibility
        boolean isEligible = creditScore >= 650 && dti <= 0.5;
        result.setIsEligible(isEligible);
        
        // Calculate max eligible amount
        double maxAmount = 0.0;
        if (isEligible) {
            double availableMonthly = monthlyIncome * 0.5 - monthlyExpenses - existingEmi;
            if (availableMonthly > 0) {
                maxAmount = availableMonthly * loanRequest.getTenureMonths();
            }
            // Cap at savings * 5 or requested amount
            maxAmount = Math.min(maxAmount, savings * 5);
            maxAmount = Math.min(maxAmount, loanRequest.getRequestedAmount() * 2);
        }
        result.setMaxEligibleAmount(maxAmount);
        
        // Calculate suggested EMI
        double suggestedEmi = 0.0;
        if (maxAmount > 0 && loanRequest.getTenureMonths() > 0) {
            double rate = creditScore > 750 ? 0.08 : 0.12; // Interest rate based on credit score
            double monthlyRate = rate / 12;
            suggestedEmi = maxAmount * monthlyRate * Math.pow(1 + monthlyRate, loanRequest.getTenureMonths()) /
                          (Math.pow(1 + monthlyRate, loanRequest.getTenureMonths()) - 1);
        }
        result.setSuggestedEmi(suggestedEmi);
        
        // Set reason
        if (!isEligible) {
            if (creditScore < 650) result.setReason("Credit score too low");
            else if (dti > 0.5) result.setReason("Debt-to-income ratio too high");
            else result.setReason("Not eligible based on financial profile");
        } else {
            result.setReason("Eligible based on current financial standing");
        }

        return eligibilityResultRepository.save(result);
    }

    @Override
    public EligibilityResult getByLoanRequestId(Long loanRequestId) {
        return eligibilityResultRepository.findByLoanRequestId(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Eligibility result not found"));
    }
}