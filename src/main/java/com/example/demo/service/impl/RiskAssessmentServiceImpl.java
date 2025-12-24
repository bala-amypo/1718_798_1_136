package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.service.RiskAssessmentService;

public class RiskAssessmentServiceImpl implements RiskAssessmentService {
    private final LoanRequestRepository loanRepo;
    private final FinancialProfileRepository profileRepo;
    private final RiskAssessmentRepository riskRepo;

    public RiskAssessmentServiceImpl(LoanRequestRepository loanRepo, FinancialProfileRepository profileRepo, RiskAssessmentRepository riskRepo) {
        this.loanRepo = loanRepo;
        this.profileRepo = profileRepo;
        this.riskRepo = riskRepo;
    }

    @Override
    public RiskAssessment assessRisk(Long loanRequestId) {
        if (riskRepo.findByLoanRequestId(loanRequestId).isPresent()) {
            throw new BadRequestException("Risk already assessed");
        }
        LoanRequest req = loanRepo.findById(loanRequestId).orElseThrow();
        FinancialProfile fp = profileRepo.findByUserId(req.getUser().getId()).orElseThrow();
        
        RiskAssessment ra = new RiskAssessment();
        ra.setLoanRequestId(loanRequestId);
        ra.setRiskScore(60.0);
        ra.setDtiRatio(fp.getMonthlyIncome() > 0 ? ((fp.getMonthlyExpenses() + (fp.getExistingLoanEmi() != null ? fp.getExistingLoanEmi() : 0)) / fp.getMonthlyIncome()) : 0.0);
        return riskRepo.save(ra);
    }

    @Override
    public RiskAssessment getByLoanRequestId(Long id) {
        return riskRepo.findByLoanRequestId(id).orElse(null);
    }
}