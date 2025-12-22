package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.service.RiskAssessmentService;

@RestController
@RequestMapping("/api/risk-logs")
public class RiskLogController {

    private final RiskAssessmentService service;

    public RiskLogController(RiskAssessmentService service) {
        this.service = service;
    }

    @GetMapping("/{loanRequestId}")
    public RiskAssessmentLog getRisk(@PathVariable Long loanRequestId) {
        return service.assessRisk(loanRequestId);
    }
}
