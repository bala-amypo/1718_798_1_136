package com.example.demo.service;

import com.example.demo.entity.RiskAssessmentLog;
import java.util.List;

public interface RiskAssessmentService {

    RiskAssessmentLog save(RiskAssessmentLog log);

    List<RiskAssessmentLog> getAll();
}
