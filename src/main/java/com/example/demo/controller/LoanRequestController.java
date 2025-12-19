package com.example.demo.controller;

import com.example.demo.dto.LoanDtos;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.User;
import com.example.demo.service.LoanRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loan-requests")
@Tag(name = "LoanRequest", description = "Loan Request management endpoints")
public class LoanRequestController {
    
    private final LoanRequestService loanRequestService;
    
    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }
    
    @PostMapping("/")
    public ResponseEntity<LoanDtos.LoanRequestDto> submitRequest(@RequestBody LoanRequest request) {
        LoanRequest savedRequest = loanRequestService.submitRequest(request);
        
        LoanDtos.LoanRequestDto dto = new LoanDtos.LoanRequestDto(
                savedRequest.getId(),
                savedRequest.getUser().getId(),
                savedRequest.getRequestedAmount(),
                savedRequest.getTenureMonths(),
                savedRequest.getPurpose(),
                savedRequest.getStatus(),
                savedRequest.getAppliedAt()
        );
        
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanDtos.LoanRequestDto>> getRequestsByUser(@PathVariable Long userId) {
        List<LoanRequest> requests = loanRequestService.getRequestsByUser(userId);
        
        List<LoanDtos.LoanRequestDto> dtos = requests.stream()
                .map(request -> new LoanDtos.LoanRequestDto(
                        request.getId(),
                        request.getUser().getId(),
                        request.getRequestedAmount(),
                        request.getTenureMonths(),
                        request.getPurpose(),
                        request.getStatus(),
                        request.getAppliedAt()
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LoanDtos.LoanRequestDto> getById(@PathVariable Long id) {
        LoanRequest request = loanRequestService.getById(id);
        
        LoanDtos.LoanRequestDto dto = new LoanDtos.LoanRequestDto(
                request.getId(),
                request.getUser().getId(),
                request.getRequestedAmount(),
                request.getTenureMonths(),
                request.getPurpose(),
                request.getStatus(),
                request.getAppliedAt()
        );
        
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<LoanDtos.LoanRequestDto>> getAllRequests() {
        List<LoanRequest> requests = loanRequestService.getAllRequests();
        
        List<LoanDtos.LoanRequestDto> dtos = requests.stream()
                .map(request -> new LoanDtos.LoanRequestDto(
                        request.getId(),
                        request.getUser().getId(),
                        request.getRequestedAmount(),
                        request.getTenureMonths(),
                        request.getPurpose(),
                        request.getStatus(),
                        request.getAppliedAt()
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }
}