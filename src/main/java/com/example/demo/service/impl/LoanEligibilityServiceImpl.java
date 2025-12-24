package com.example.demo.service.impl;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanEligibilityService;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {

    @Override
    public EligibilityResult evaluateEligibility(FinancialProfile profile, LoanRequest request) {

        double availableIncome = profile.getMonthlyIncome()
                - profile.getMonthlyExpenses()
                - profile.getExistingLoanEmi();

        double monthlyEmi = calculateEmi(request.getRequestedAmount(), 10.0, request.getTenureMonths());

        EligibilityResult result = new EligibilityResult();
        result.setApproved(availableIncome > monthlyEmi);
        result.setMaxEligibleAmount(Math.max(0, availableIncome * 10));
        result.setEmi(monthlyEmi);

        return result;
    }

    @Override
    public double calculateEmi(double principal, double rate, int months) {
        double monthlyRate = rate / 1200;
        if (monthlyRate == 0) return principal / months;
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);
    }
}
