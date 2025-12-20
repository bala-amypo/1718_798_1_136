package com.example.demo.controller;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.service.RiskAssessmentLogService;  // Correct service name
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk-logs")
@Tag(name = "Risk Logs", description = "Risk assessment log endpoints")
public class RiskLogController {
    
    private final RiskAssessmentLogService riskAssessmentLogService;
    
    public RiskLogController(RiskAssessmentLogService riskAssessmentLogService) {
        this.riskAssessmentLogService = riskAssessmentLogService;
    }
    
    @GetMapping("/{loanRequestId}")
    @Operation(summary = "Get risk assessment logs by loan request ID")
    public ResponseEntity<List<RiskAssessmentLog>> getLogsByRequest(@PathVariable Long loanRequestId) {
        List<RiskAssessmentLog> logs = riskAssessmentLogService.getLogsByRequest(loanRequestId);
        return ResponseEntity.ok(logs);
    }
}