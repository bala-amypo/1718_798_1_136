package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.EligibilityResult;
import com.example.demo.service.LoanEligibilityService;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {

    @Override
    public EligibilityResult evaluateEligibility(FinancialProfile profile, LoanRequest request) {

        double income = profile.getMonthlyIncome();
        double existingEmi = profile.getExistingLoanEmi();
        double requestAmount = request.getRequestedAmount();

        EligibilityResult result = new EligibilityResult();

        if (income - existingEmi >= requestAmount / 12) {
            result.setApproved(true);
            result.setEmi(requestAmount / 12);
        } else {
            result.setApproved(false);
            result.setEmi(0);
        }

        return result;
    }
}
