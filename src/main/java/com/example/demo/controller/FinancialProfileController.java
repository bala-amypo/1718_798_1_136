package com.example.demo.controller;

import com.example.demo.dto.LoanDtos.FinancialProfileDto;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.service.FinancialProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial-profiles")
@Tag(name = "Financial Profiles", description = "Financial profile management endpoints")
public class FinancialProfileController {
    
    private final FinancialProfileService financialProfileService;
    
    public FinancialProfileController(FinancialProfileService financialProfileService) {
        this.financialProfileService = financialProfileService;
    }
    
    @PostMapping
    @Operation(summary = "Create or update financial profile")
    public ResponseEntity<FinancialProfile> createOrUpdateProfile(@RequestBody FinancialProfileDto profileDto) {
        // Create FinancialProfile from DTO
        FinancialProfile profile = new FinancialProfile();
        profile.setMonthlyIncome(profileDto.getMonthlyIncome());
        profile.setMonthlyExpenses(profileDto.getMonthlyExpenses());
        profile.setExistingLoanEmi(profileDto.getExistingLoanEmi());
        profile.setCreditScore(profileDto.getCreditScore());
        profile.setSavingsBalance(profileDto.getSavingsBalance());
        
        FinancialProfile savedProfile = financialProfileService.createOrUpdateProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get financial profile by user ID")
    public ResponseEntity<FinancialProfileDto> getProfileByUser(@PathVariable Long userId) {
        FinancialProfile profile = financialProfileService.getProfileByUser(userId);
        
        FinancialProfileDto dto = new FinancialProfileDto(
            profile.getMonthlyIncome(),
            profile.getMonthlyExpenses(),
            profile.getExistingLoanEmi(),
            profile.getCreditScore(),
            profile.getSavingsBalance()
        );
        
        return ResponseEntity.ok(dto);
    }
}