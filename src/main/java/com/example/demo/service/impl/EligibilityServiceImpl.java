package com.example.demo.service.impl;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.repository.EligibilityResultRepository;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.service.EligibilityService;
import org.springframework.stereotype.Service;

@Service
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

        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(req);
        result.setMaxEligibleAmount(fp.getMonthlyIncome() * 12);
        result.setIsEligible(fp.getCreditScore() != null && fp.getCreditScore() >= 600);
        result.setEstimatedEmi(result.getMaxEligibleAmount() / req.getTenureMonths());
        result.setRiskLevel(fp.getCreditScore() > 750 ? "LOW" : "MEDIUM");

        return resultRepo.save(result);
    }

    @Override
    public EligibilityResult getByLoanRequestId(Long loanRequestId) {
        return resultRepo.findByLoanRequestId(loanRequestId).orElse(null);
    }
}