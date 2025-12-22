package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentService;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private final RiskAssessmentLogRepository repo;

    public RiskAssessmentServiceImpl(RiskAssessmentLogRepository repo) {
        this.repo = repo;
    }

    public RiskAssessmentLog assessRisk(Long loanRequestId) {
        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setLoanRequestId(loanRequestId);
        log.setDtiRatio(0.3);
        log.setStatus("APPROVED");
        return repo.save(log);
    }
}
