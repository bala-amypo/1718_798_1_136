package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {
    
    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final RiskAssessmentLogRepository riskAssessmentLogRepository;
    
    public RiskAssessmentServiceImpl(LoanRequestRepository loanRequestRepository,
                                    FinancialProfileRepository financialProfileRepository,
                                    RiskAssessmentLogRepository riskAssessmentLogRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.riskAssessmentLogRepository = riskAssessmentLogRepository;
    }
    
    @Override
    public RiskAssessmentLog assessRisk(Long loanRequestId) {
        // Check if already assessed
        List<RiskAssessmentLog> existingLogs = riskAssessmentLogRepository.findByLoanRequestId(loanRequestId);
        if (!existingLogs.isEmpty()) {
            throw new BadRequestException("Risk already assessed");
        }
        
        // Get loan request
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found"));
        
        // Get financial profile
        FinancialProfile profile = financialProfileRepository.findByUserId(loanRequest.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found"));
        
        // Calculate DTI
        Double totalMonthlyObligations = profile.getMonthlyExpenses() + 
                (profile.getExistingLoanEmi() != null ? profile.getExistingLoanEmi() : 0);
        Double dtiRatio = totalMonthlyObligations / profile.getMonthlyIncome();
        
        // Determine credit check status
        String creditCheckStatus = "APPROVED";
        if (profile.getCreditScore() < 600) {
            creditCheckStatus = "REJECTED";
        } else if (profile.getCreditScore() < 700) {
            creditCheckStatus = "PENDING_REVIEW";
        }
        
        // Create risk assessment log
        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setLoanRequestId(loanRequestId);
        log.setDtiRatio(dtiRatio);
        log.setCreditCheckStatus(creditCheckStatus);
        
        return riskAssessmentLogRepository.save(log);
    }
    
    @Override
    public RiskAssessmentLog getByLoanRequestId(Long loanRequestId) {
        List<RiskAssessmentLog> logs = riskAssessmentLogRepository.findByLoanRequestId(loanRequestId);
        if (logs.isEmpty()) {
            throw new ResourceNotFoundException("Risk assessment log not found");
        }
        return logs.get(0);
    }
}