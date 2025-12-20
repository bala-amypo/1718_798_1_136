package com.example.demo.service.impl;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentLogService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RiskAssessmentLogServiceImpl implements RiskAssessmentLogService {
    
    private final RiskAssessmentLogRepository riskAssessmentLogRepository;
    
    public RiskAssessmentLogServiceImpl(RiskAssessmentLogRepository riskAssessmentLogRepository) {
        this.riskAssessmentLogRepository = riskAssessmentLogRepository;
    }
    
    @Override
    public RiskAssessmentLog logAssessment(RiskAssessmentLog log) {
        return riskAssessmentLogRepository.save(log);
    }
    
    @Override
    public List<RiskAssessmentLog> getLogsByRequest(Long requestId) {
        return riskAssessmentLogRepository.findByLoanRequestid(requestId);
    }
}