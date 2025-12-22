package com.example.demo.controller;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.service.RiskAssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
public class RiskLogController {

    private final RiskAssessmentService service;

    public RiskLogController(RiskAssessmentService service) {
        this.service = service;
    }

    @Operation(summary = "Assess risk for a user")
    @GetMapping("/{userId}")
    public RiskAssessmentLog assessRisk(@PathVariable Long userId) {
        return service.assessRisk(userId);
    }
}
