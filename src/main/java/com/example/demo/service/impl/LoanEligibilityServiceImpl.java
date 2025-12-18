package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.LoanEligibilityService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {

    private final LoanRequestRepository loanRepo;
    private final FinancialProfileRepository profileRepo;
    private final EligibilityResultRepository resultRepo;

    public LoanEligibilityServiceImpl(
            LoanRequestRepository loanRepo,
            FinancialProfileRepository profileRepo,
            EligibilityResultRepository resultRepo) {
        this.loanRepo = loanRepo;
        this.profileRepo = profileRepo;
        this.resultRepo = resultRepo;
    }

    @Override
    public EligibilityResult evaluate(Long loanRequestId) {

        LoanRequest loan = loanRepo.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        FinancialProfile profile = profileRepo.findByUserId(
                loan.getUser().getId()
        );

        double dti = (profile.getMonthlyExpenses() + profile.getExistingLoanEmi())
                / profile.getMonthlyIncome();

        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(loan);
        result.setCalculatedAt(new Timestamp(System.currentTimeMillis()));

        if (profile.getCreditScore() >= 650 && dti < 0.6) {
            result.setIsEligible(true);
            result.setRiskLevel(dti < 0.3 ? "LOW" : "MEDIUM");
            result.setMaxEligibleAmount(loan.getRequestedAmount());
        } else {
            result.setIsEligible(false);
            result.setRiskLevel("HIGH");
            result.setRejectionReason("Low credit score or high debt");
        }

        return resultRepo.save(result);
    }
}
