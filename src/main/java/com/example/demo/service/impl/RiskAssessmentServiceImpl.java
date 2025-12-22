package com.example.demo.service.impl;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentService;

import java.util.ArrayList;
import java.util.List;

public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private final RiskAssessmentLogRepository repo;

    public RiskAssessmentServiceImpl(RiskAssessmentLogRepository repo) {
        this.repo = repo;
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
