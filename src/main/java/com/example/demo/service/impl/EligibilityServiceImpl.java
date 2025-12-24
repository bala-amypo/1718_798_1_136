package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.service.EligibilityService;

public class EligibilityServiceImpl implements EligibilityService {
    private final LoanRequestRepository loanRepo;
    private final FinancialProfileRepository profileRepo;
    private final EligibilityResultRepository resultRepo;

    public EligibilityServiceImpl(LoanRequestRepository loanRepo, FinancialProfileRepository profileRepo, EligibilityResultRepository resultRepo) {
        this.loanRepo = loanRepo;
        this.profileRepo = profileRepo;
        this.resultRepo = resultRepo;
    }

    @Override
    public EligibilityResult evaluateEligibility(Long loanRequestId) {
        if (resultRepo.findByLoanRequestId(loanRequestId).isPresent()) {
            throw new BadRequestException("Eligibility already evaluated");
        }
        LoanRequest req = loanRepo.findById(loanRequestId).orElseThrow();
        FinancialProfile fp = profileRepo.findByUserId(req.getUser().getId()).orElseThrow();
        
        EligibilityResult res = new EligibilityResult();
        res.setLoanRequest(req);
        res.setMaxEligibleAmount(fp.getMonthlyIncome() * 12);
        res.setIsEligible(fp.getCreditScore() >= 650);
        return resultRepo.save(res);
    }

    @Override
    public EligibilityResult getByLoanRequestId(Long id) {
        return resultRepo.findByLoanRequestId(id).orElse(null);
    }
}