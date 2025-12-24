package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessment;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.RiskAssessmentRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.stereotype.Service;

@Service
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
        ra.setRiskScore(70.0);
        
        double totalMonthlyObligations = (fp.getMonthlyExpenses() != null ? fp.getMonthlyExpenses() : 0) + 
                                       (fp.getExistingLoanEmi() != null ? fp.getExistingLoanEmi() : 0);
        ra.setDtiRatio(fp.getMonthlyIncome() > 0 ? (totalMonthlyObligations / fp.getMonthlyIncome()) : 0.0);
        ra.setCreditCheckStatus("APPROVED");

        return riskRepo.save(ra);
    }

    @Override
    public RiskAssessment getByLoanRequestId(Long loanRequestId) {
        return riskRepo.findByLoanRequestId(loanRequestId).orElse(null);
    }
}