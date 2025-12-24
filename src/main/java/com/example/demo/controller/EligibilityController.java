package com.example.demo.controller;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.EligibilityResult;
import com.example.demo.service.FinancialProfileService;
import com.example.demo.service.LoanEligibilityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eligibility")
public class EligibilityController {

    private final FinancialProfileService profileService;
    private final LoanEligibilityService eligibilityService;

    public EligibilityController(FinancialProfileService profileService,
                                 LoanEligibilityService eligibilityService) {
        this.profileService = profileService;
        this.eligibilityService = eligibilityService;
    }

    @GetMapping("/{userId}")
    public EligibilityResult check(@PathVariable Long userId) {
        FinancialProfile fp = profileService.getByUserId(userId);
        LoanRequest req = new LoanRequest();
        return eligibilityService.evaluateEligibility(fp, req);
    }
}
