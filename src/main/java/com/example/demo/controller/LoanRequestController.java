package com.example.demo.controller;

import com.example.demo.dto.LoanDtos.LoanRequestDto;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loan-requests")
@Tag(name = "Loan Requests", description = "Loan request management endpoints")
public class LoanRequestController {
    
    private final LoanRequestService loanRequestService;
    
    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }
    
    @PostMapping
    @Operation(summary = "Submit a new loan request")
    public ResponseEntity<LoanRequest> submitLoanRequest(@RequestBody LoanRequestDto loanRequestDto) {
        // Create LoanRequest from DTO
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setRequestedAmount(loanRequestDto.getRequestedAmount());
        loanRequest.setTenureMonths(loanRequestDto.getTenureMonths());
        loanRequest.setPurpose(loanRequestDto.getPurpose());
        
        LoanRequest savedRequest = loanRequestService.submitLoanRequest(loanRequest);
        return ResponseEntity.ok(savedRequest);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get loan requests by user ID")
    public ResponseEntity<List<LoanRequestDto>> getRequestsByUser(@PathVariable Long userId) {
        List<LoanRequest> requests = loanRequestService.getRequestsByUser(userId);
        
        List<LoanRequestDto> dtos = requests.stream()
            .map(request -> new LoanRequestDto(
                request.getRequestedAmount(),
                request.getTenureMonths(),
                request.getPurpose()
            ))
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get loan request by ID")
    public ResponseEntity<LoanRequestDto> getRequestById(@PathVariable Long id) {
        LoanRequest request = loanRequestService.getRequestById(id);
        
        LoanRequestDto dto = new LoanRequestDto(
            request.getRequestedAmount(),
            request.getTenureMonths(),
            request.getPurpose()
        );
        
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping
    @Operation(summary = "Get all loan requests")
    public ResponseEntity<List<LoanRequestDto>> getAllRequests() {
        List<LoanRequest> requests = loanRequestService.getAllRequests();
        
        List<LoanRequestDto> dtos = requests.stream()
            .map(request -> new LoanRequestDto(
                request.getRequestedAmount(),
                request.getTenureMonths(),
                request.getPurpose()
            ))
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(dtos);
    }
}