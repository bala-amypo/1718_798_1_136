package com.example.demo.controller;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.service.LoanEligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityController {
    
    private final LoanEligibilityService loanEligibilityService;
    
    @Autowired
    public EligibilityController(LoanEligibilityService loanEligibilityService) {
        this.loanEligibilityService = loanEligibilityService;
    }
    
    @PostMapping("/evaluate/{loanRequestId}")
    public ResponseEntity<EligibilityResult> evaluateEligibility(@PathVariable Long loanRequestId) {
        EligibilityResult result = loanEligibilityService.evaluateEligibility(loanRequestId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/result/{loanRequestId}")
    public ResponseEntity<EligibilityResult> getResult(@PathVariable Long loanRequestId) {
        EligibilityResult result = loanEligibilityService.getByLoanRequestId(loanRequestId);
        return ResponseEntity.ok(result);
    }
}