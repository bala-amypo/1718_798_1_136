package com.example.demo.controller;

import com.example.demo.dto.LoanDtos;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.service.FinancialProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial-profiles")
public class FinancialProfileController {
    
    private final FinancialProfileService financialProfileService;
    
    @Autowired
    public FinancialProfileController(FinancialProfileService financialProfileService) {
        this.financialProfileService = financialProfileService;
    }
    
    @PostMapping("/")
    public ResponseEntity<FinancialProfile> createOrUpdate(@RequestBody FinancialProfile profile) {
        FinancialProfile savedProfile = financialProfileService.createOrUpdate(profile); // Fixed method name
        return ResponseEntity.ok(savedProfile);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<LoanDtos.FinancialProfileDto> getByUserId(@PathVariable Long userId) {
        FinancialProfile profile = financialProfileService.getByUserId(userId);

        LoanDtos.FinancialProfileDto dto = new LoanDtos.FinancialProfileDto();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setMonthlyIncome(profile.getMonthlyIncome());
        dto.setMonthlyExpenses(profile.getMonthlyExpenses());
        dto.setExistingLoanEmi(profile.getExistingLoanEmi());
        dto.setCreditScore(profile.getCreditScore());
        dto.setSavingsBalance(profile.getSavingsBalance());
        dto.setLastUpdatedAt(profile.getLastUpdatedAt());
        
        return ResponseEntity.ok(dto);
    }
}