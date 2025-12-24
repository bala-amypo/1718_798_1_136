package com.example.demo.controller;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.service.EligibilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityController {
    private final EligibilityService eligibilityService;

    public EligibilityController(EligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    @PostMapping("/evaluate/{loanRequestId}")
    public ResponseEntity<EligibilityResult> evaluate(@PathVariable Long loanRequestId) {
        return ResponseEntity.ok(eligibilityService.evaluateEligibility(loanRequestId));
    }

    @GetMapping("/result/{loanRequestId}")
    public ResponseEntity<EligibilityResult> getResult(@PathVariable Long loanRequestId) {
        return ResponseEntity.ok(eligibilityService.getByLoanRequestId(loanRequestId));
    }
}