package com.example.demo.controller;

import com.example.demo.dto.EligibilityResultDto;
import com.example.demo.entity.EligibilityResult;
import com.example.demo.service.LoanEligibilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eligibility")
@Tag(name = "Eligibility", description = "Loan Eligibility evaluation endpoints")
public class EligibilityController {
    
    private final LoanEligibilityService loanEligibilityService;
    
    public EligibilityController(LoanEligibilityService loanEligibilityService) {
        this.loanEligibilityService = loanEligibilityService;
    }
    
    @PostMapping("/evaluate/{loanRequestId}")
    public ResponseEntity<EligibilityResultDto> evaluateEligibility(@PathVariable Long loanRequestId) {
        EligibilityResult result = loanEligibilityService.evaluateEligibility(loanRequestId);
        
        EligibilityResultDto dto = new EligibilityResultDto(
                result.getId(),
                result.getLoanRequest().getId(),
                result.getIsEligible(),
                result.getMaxEligibleAmount(),
                result.getEstimatedEmi(),
                result.getRiskLevel(),
                result.getRejectionReason(),
                result.getCalculatedAt()
        );
        
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/result/{loanRequestId}")
    public ResponseEntity<EligibilityResultDto> getResult(@PathVariable Long loanRequestId) {
        EligibilityResult result = loanEligibilityService.getByLoanRequestId(loanRequestId);
        
        EligibilityResultDto dto = new EligibilityResultDto(
                result.getId(),
                result.getLoanRequest().getId(),
                result.getIsEligible(),
                result.getMaxEligibleAmount(),
                result.getEstimatedEmi(),
                result.getRiskLevel(),
                result.getRejectionReason(),
                result.getCalculatedAt()
        );
        
        return ResponseEntity.ok(dto);
    }
}