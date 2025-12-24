package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.EligibilityResult;
import com.example.demo.service.LoanEligibilityService;

@RestController
@RequestMapping("/eligibility")
public class EligibilityController {

    @Autowired
    private LoanEligibilityService service;

    @PostMapping("/check")
    public EligibilityResult checkEligibility(@RequestBody LoanRequest request) {
        FinancialProfile profile = new FinancialProfile();
        profile.setUserId(request.getUserId());
        
        return service.evaluateEligibility(profile, request);
    }
}
