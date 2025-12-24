package com.example.demo.controller;

import com.example.demo.entity.RiskAssessment;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk-logs")
public class RiskLogController {
    private final RiskAssessmentService riskAssessmentService;

    public RiskLogController(RiskAssessmentService riskAssessmentService) {
        this.riskAssessmentService = riskAssessmentService;
    }

    @GetMapping("/{loanRequestId}")
    public ResponseEntity<RiskAssessment> getRiskLog(@PathVariable Long loanRequestId) {
        return ResponseEntity.ok(riskAssessmentService.getByLoanRequestId(loanRequestId));
    }
}