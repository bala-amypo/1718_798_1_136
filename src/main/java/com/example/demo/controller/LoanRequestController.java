package com.example.demo.controller;

import com.example.demo.dto.LoanDto;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@CrossOrigin
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @PostMapping("/")
    public ResponseEntity<LoanRequest> createLoan(@RequestBody LoanDto dto) {
        LoanRequest lr = loanRequestService.createLoanRequest(dto);
        return ResponseEntity.ok(lr);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanRequest> getLoan(@PathVariable Long loanId) {
        LoanRequest lr = loanRequestService.getById(loanId);
        return ResponseEntity.ok(lr);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserLoans(@PathVariable Long userId) {
        return ResponseEntity.ok(loanRequestService.getRequestsByUser(userId));
    }
}
