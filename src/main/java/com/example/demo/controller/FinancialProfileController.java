package com.example.demo.controller;

import com.example.demo.dto.LoanDtos;
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

    /* ================= CREATE / UPDATE ================= */

    @PostMapping
    @Operation(summary = "Create or update financial profile")
    public ResponseEntity<FinancialProfile> createOrUpdateProfile(
            @RequestBody FinancialProfile profile) {

        FinancialProfile savedProfile =
                financialProfileService.createOrUpdateProfile(profile);

        return ResponseEntity.ok(savedProfile);
    }

    /* ================= GET BY USER ================= */

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get financial profile by user ID")
    public ResponseEntity<LoanDtos.FinancialProfileDto> getProfileByUser(
            @PathVariable Long userId) {

        FinancialProfile profile =
                financialProfileService.getProfileByUser(userId);

        LoanDtos.FinancialProfileDto dto =
                new LoanDtos.FinancialProfileDto();

        dto.setMonthlyIncome(profile.getMonthlyIncome());
        dto.setMonthlyExpenses(profile.getMonthlyExpenses());
        dto.setExistingLoanEmi(profile.getExistingLoanEmi());
        dto.setCreditScore(profile.getCreditScore());
        dto.setSavingsBalance(profile.getSavingsBalance());

        return ResponseEntity.ok(dto);
    }
}
