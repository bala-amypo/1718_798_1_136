package com.example.demo.controller;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.service.LoanEligibilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eligibility")
@Tag(name = "Eligibility", description = "Loan eligibility evaluation endpoints")
public class EligibilityController {
    
    private final LoanEligibilityService loanEligibilityService;
    
    public EligibilityController(LoanEligibilityService loanEligibilityService) {
        this.loanEligibilityService = loanEligibilityService;
    }
    
    @PostMapping("/evaluate/{loanRequestId}")
    @Operation(summary = "Evaluate eligibility for a loan request")
    public ResponseEntity<EligibilityResult> evaluateEligibility(@PathVariable Long loanRequestId) {
        EligibilityResult result = loanEligibilityService.evaluateEligibility(loanRequestId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/result/{loanRequestId}")
    @Operation(summary = "Get eligibility result for a loan request")
    public ResponseEntity<EligibilityResult> getResult(@PathVariable Long loanRequestId) {
        EligibilityResult result = loanEligibilityService.getResultByRequest(loanRequestId);
        return ResponseEntity.ok(result);
    }
}