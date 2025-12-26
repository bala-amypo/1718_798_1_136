package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessment;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.RiskAssessmentRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException; // Ensure this is imported
import com.example.demo.service.RiskAssessmentService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

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

        LoanRequest req = loanRepo.findById(loanRequestId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan request not found"));
        
        FinancialProfile fp = profileRepo.findByUserId(req.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found"));

        RiskAssessment ra = new RiskAssessment();
        ra.setLoanRequestId(loanRequestId);
        
        double totalMonthlyObligations = (fp.getMonthlyExpenses() != null ? fp.getMonthlyExpenses() : 0) + 
                                       (fp.getExistingLoanEmi() != null ? fp.getExistingLoanEmi() : 0);
        
        double dti = fp.getMonthlyIncome() > 0 ? (totalMonthlyObligations / fp.getMonthlyIncome()) : 0.0;
        ra.setDtiRatio(dti);
        
        ra.setRiskScore(70.0);
        ra.setCreditCheckStatus("APPROVED");
        ra.setTimestamp(LocalDateTime.now());

        return riskRepo.save(ra);
    }

    @Override
    public RiskAssessment getByLoanRequestId(Long loanRequestId) {
        return riskRepo.findByLoanRequestId(loanRequestId)
            .orElseThrow(() -> new ResourceNotFoundException("Risk assessment not found"));
    }
}