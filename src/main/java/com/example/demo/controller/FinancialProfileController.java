package com.example.demo.controller;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.service.FinancialProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class FinancialProfileController {

    private final FinancialProfileService financialProfileService;

    public FinancialProfileController(FinancialProfileService financialProfileService) {
        this.financialProfileService = financialProfileService;
    }

    @PostMapping("/")
    public ResponseEntity<FinancialProfile> createOrUpdate(@RequestBody FinancialProfile fp) {
        FinancialProfile saved = financialProfileService.createOrUpdate(fp);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<FinancialProfile> getByUser(@PathVariable Long userId) {
        FinancialProfile fp = financialProfileService.getByUserId(userId);
        return ResponseEntity.ok(fp);
    }
}
