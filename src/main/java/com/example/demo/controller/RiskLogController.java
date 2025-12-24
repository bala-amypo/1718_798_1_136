package com.example.demo.controller;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.RiskAssessmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risk")
public class RiskLogController {

    @Autowired
    private RiskAssessmentService riskAssessmentService;

    @PostMapping("/assess")
    public RiskAssessmentLog assessRisk(@RequestBody LoanRequest request) {

        FinancialProfile profile = new FinancialProfile();
        profile.setUserId(request.getUserId());

        return riskAssessmentService.assessRisk(profile, request);
    }
}
