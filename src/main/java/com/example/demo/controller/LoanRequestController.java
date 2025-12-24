package com.example.demo.controller;

import com.example.demo.dto.LoanDto;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @PostMapping("/create")
    public LoanRequest createLoan(@RequestBody LoanDto dto) {

        LoanRequest request = new LoanRequest(
                dto.getUserId(),
                dto.getLoanAmount(),
                dto.getInterestRate(),
                dto.getTenureMonths()
        );

        return loanRequestService.create(request);
    }

    @GetMapping("/{id}")
    public LoanRequest getLoan(@PathVariable Long id) {
        return loanRequestService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<LoanRequest> getLoansByUser(@PathVariable Long userId) {
        return loanRequestService.getByUserId(userId);
    }
}
