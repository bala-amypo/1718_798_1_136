package com.example.demo.controller;

import com.example.demo.dto.LoanDtos;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.User;
import com.example.demo.service.FinancialProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/financial-profiles")
@Tag(name = "FinancialProfile", description = "Financial Profile management endpoints")
public class FinancialProfileController {
    
    private final FinancialProfileService financialProfileService;
    
    public FinancialProfileController(FinancialProfileService financialProfileService) {
        this.financialProfileService = financialProfileService;
    }
    
    @PostMapping("/")
    public ResponseEntity<LoanDtos.FinancialProfileDto> createOrUpdate(@RequestBody FinancialProfile profile) {
        FinancialProfile savedProfile = financialProfileService.createOrUpdate(profile);
        
        LoanDtos.FinancialProfileDto dto = new LoanDtos.FinancialProfileDto(
                savedProfile.getId(),
                savedProfile.getUser().getId(),
                savedProfile.getMonthlyIncome(),
                savedProfile.getMonthlyExpenses(),
                savedProfile.getExistingLoanEmi(),
                savedProfile.getCreditScore(),
                savedProfile.getSavingsBalance(),
                savedProfile.getLastUpdatedAt()
        );
        
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<LoanDtos.FinancialProfileDto> getByUserId(@PathVariable Long userId) {
        FinancialProfile profile = financialProfileService.getByUserId(userId);
        
        LoanDtos.FinancialProfileDto dto = new LoanDtos.FinancialProfileDto(
                profile.getId(),
                profile.getUser().getId(),
                profile.getMonthlyIncome(),
                profile.getMonthlyExpenses(),
                profile.getExistingLoanEmi(),
                profile.getCreditScore(),
                profile.getSavingsBalance(),
                profile.getLastUpdatedAt()
        );
        
        return ResponseEntity.ok(dto);
    }
}