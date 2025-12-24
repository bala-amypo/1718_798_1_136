package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk-logs")
public class RiskLogController {

    @GetMapping
    public String getRiskLogs() {
        return "Risk logs endpoint - Not fully implemented in basic version";
    }
}