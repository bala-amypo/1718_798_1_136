package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.RiskAssessment;

public interface RiskAssessmentLogRepository extends JpaRepository<RiskAssessment, Long> {}
