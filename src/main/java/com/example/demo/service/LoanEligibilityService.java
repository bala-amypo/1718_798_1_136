package com.example.demo.service;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;

public interface LoanEligibilityService {

    EligibilityResult evaluateEligibility(FinancialProfile profile, LoanRequest request);

    double calculateEmi(double principal, double interestRate, int tenureMonths);
}
