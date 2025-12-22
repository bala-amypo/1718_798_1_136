package com.example.demo.service.impl;
import org.springframework.stereotype.Service;
import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentService;

import java.util.ArrayList;
import java.util.List;
@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private final RiskAssessmentLogRepository repo;

    public RiskAssessmentServiceImpl(RiskAssessmentLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public RiskAssessmentLog assessRisk(Long loanRequestId) {
        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setLoanRequestId(loanRequestId);
        log.setRiskScore(0.5);
        log.setRiskLevel("MEDIUM");
        return repo.save(log);
    }

    @Override
    public RiskAssessmentLog save(RiskAssessmentLog log) {
        return repo.save(log);
    }

    @Override
    public List<RiskAssessmentLog> getAll() {
        return new ArrayList<>();
    }
}
