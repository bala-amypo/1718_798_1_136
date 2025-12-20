package com.example.demo.service.impl;

import com.example.demo.entity.LoanRequest;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.service.LoanRequestService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {
    
    private final LoanRequestRepository loanRequestRepository;
    
    public LoanRequestServiceImpl(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }
    
    @Override
    public LoanRequest submitLoanRequest(LoanRequest request) {
        if (request.getRequestedAmount() == null || request.getRequestedAmount() <= 0) {
            throw new IllegalArgumentException("Requested amount must be greater than 0");
        }
        
        if (request.getStatus() == null || request.getStatus().isEmpty()) {
            request.setStatus("PENDING");
        }
        
        request.setAppliedAt(LocalDateTime.now());
        return loanRequestRepository.save(request);
    }
    
    @Override
    public List<LoanRequest> getRequestsByUser(Long userId) {
        return loanRequestRepository.findByUserid(userId);
    }
    
    @Override
    public LoanRequest getRequestById(Long id) {
        return loanRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan request not found"));
    }
    
    @Override
    public List<LoanRequest> getAllRequests() {
        return loanRequestRepository.findAll();
    }
}