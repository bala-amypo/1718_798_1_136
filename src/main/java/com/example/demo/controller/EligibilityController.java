package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.EligibilityResult;
import com.example.demo.service.LoanEligibilityService;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityController {

    private final LoanEligibilityService service;

    public EligibilityController(LoanEligibilityService service) {
        this.service = service;
    }

    @PostMapping("/evaluate/{loanRequestId}")
    public EligibilityResult evaluate(@PathVariable Long loanRequestId) {
        return service.evaluate(loanRequestId);
    }
}
