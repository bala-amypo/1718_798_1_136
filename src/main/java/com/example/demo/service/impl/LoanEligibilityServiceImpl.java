package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.LoanEligibilityService;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {

    private final LoanRequestRepository loanRepo;
    private final EligibilityResultRepository resultRepo;

    public LoanEligibilityServiceImpl(LoanRequestRepository loanRepo,
      EligibilityResultRepository resultRepo) {
        this.loanRepo = loanRepo;
        this.resultRepo = resultRepo;
    }

    public EligibilityResult evaluate(Long loanRequestId) {
        LoanRequest req = loanRepo.findById(loanRequestId).orElse(null);

        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(req);
        result.setEligible(true);
        result.setEligibleAmount(req.getRequestedAmount());
        result.setRiskLevel("LOW");

        return resultRepo.save(result);
    }
}
