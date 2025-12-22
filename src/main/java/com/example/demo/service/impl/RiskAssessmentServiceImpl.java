package com.example.demo.service.impl;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.stereotype.Service;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private final RiskAssessmentLogRepository repository;

    public RiskAssessmentServiceImpl(RiskAssessmentLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public RiskAssessmentLog logRisk(Long userId, Double amount, String riskLevel) {

        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setUserId(userId);
        log.setAmount(amount);
        log.setRiskLevel(riskLevel);

        return repository.save(log);
    }
}
