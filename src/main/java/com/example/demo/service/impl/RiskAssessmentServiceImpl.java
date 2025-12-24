package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.RiskAssessmentRepository;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.stereotype.Service;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {
    
    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    
    public RiskAssessmentServiceImpl(LoanRequestRepository loanRequestRepository,
                                    FinancialProfileRepository financialProfileRepository,
                                    RiskAssessmentRepository riskAssessmentRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.riskAssessmentRepository = riskAssessmentRepository;
    }
    
    @Override
    public RiskAssessmentLog assessRisk(Long loanRequestId) {
        // Check if already assessed
        var existingLogs = riskAssessmentRepository.findByLoanRequestId(loanRequestId);
        if (!existingLogs.isEmpty()) {
            throw new BadRequestException("Risk already assessed");
        }
        
        // Get loan request
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan request not found"));
        
        // Get financial profile
        FinancialProfile profile = financialProfileRepository.findByUserId(loanRequest.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found"));
        
        // Calculate DTI ratio
        double totalMonthlyObligations = profile.getMonthlyExpenses() + profile.getExistingLoanEmi();
        double dtiRatio = profile.getMonthlyIncome() > 0 ? 
            (totalMonthlyObligations / profile.getMonthlyIncome()) * 100 : 0.0;
        
        // Determine credit check status
        String creditCheckStatus = determineCreditCheckStatus(profile.getCreditScore(), dtiRatio);
        
        // Create risk assessment log
        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setLoanRequestId(loanRequestId);
        log.setDtiRatio(dtiRatio);
        log.setCreditCheckStatus(RiskAssessmentLog.CreditCheckStatus.valueOf(creditCheckStatus));
        
        return riskAssessmentRepository.save(log);
    }
    
    private String determineCreditCheckStatus(int creditScore, double dtiRatio) {
        if (creditScore >= 700 && dtiRatio <= 40) {
            return "APPROVED";
        } else if (creditScore >= 600 && dtiRatio <= 60) {
            return "PENDING_REVIEW";
        } else {
            return "REJECTED";
        }
    }
    
    @Override
    public RiskAssessmentLog getByLoanRequestId(Long loanRequestId) {
        var logs = riskAssessmentRepository.findByLoanRequestId(loanRequestId);
        if (logs.isEmpty()) {
            throw new ResourceNotFoundException("Risk assessment log not found");
        }
        return logs.get(0); // Return first log
    }
}