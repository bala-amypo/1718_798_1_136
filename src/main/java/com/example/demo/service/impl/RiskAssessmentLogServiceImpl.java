package com.example.demo.service.impl;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAssessmentLogServiceImpl implements RiskAssessmentLogService {

    private final RiskAssessmentLogRepository repo;

    public RiskAssessmentLogServiceImpl(RiskAssessmentLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public RiskAssessmentLog save(RiskAssessmentLog log) {
        return repo.save(log);
    }

    @Override
    public List<RiskAssessmentLog> getAll() {
        return repo.findAll();
    }
}
