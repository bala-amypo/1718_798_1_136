package com.example.demo.controller;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.service.LoanEligibilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eligibility")
@CrossOrigin
public class EligibilityController {

    private final LoanEligibilityService loanEligibilityService;

    public EligibilityController(LoanEligibilityService loanEligibilityService) {
        this.loanEligibilityService = loanEligibilityService;
    }

    @GetMapping("/{loanRequestId}")
    public ResponseEntity<EligibilityResult> getEligibility(@PathVariable Long loanRequestId) {
        EligibilityResult res = loanEligibilityService.evaluateEligibility(loanRequestId);
        return ResponseEntity.ok(res);
    }
}
