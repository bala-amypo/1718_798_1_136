package com.example.demo.controller;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/risk-logs")
public class RiskLogController {
    
    private final RiskAssessmentService riskAssessmentService;
    
    @Autowired
    public RiskLogController(RiskAssessmentService riskAssessmentService) {
        this.riskAssessmentService = riskAssessmentService;
    }
    
    @GetMapping("/{loanRequestId}")
    public ResponseEntity<List<RiskAssessmentLog>> getRiskLogs(@PathVariable Long loanRequestId) {
        List<RiskAssessmentLog> logs = riskAssessmentService.getByLoanRequestId(loanRequestId);
        return ResponseEntity.ok(logs);
    }
}