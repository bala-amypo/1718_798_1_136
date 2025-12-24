package com.example.demo.service.impl;

import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LoanRequestService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {
    
    private final LoanRequestRepository loanRequestRepository;
    private final UserRepository userRepository;
    
    public LoanRequestServiceImpl(LoanRequestRepository loanRequestRepository,
                                 UserRepository userRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public LoanRequest submitRequest(LoanRequest request) {
        // Validate user exists
        User user = userRepository.findById(request.getUser().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Validate requested amount
        if (request.getRequestedAmount() <= 0) {
            throw new BadRequestException("Requested amount must be greater than 0");
        }
        
        // Validate tenure months
        if (request.getTenureMonths() <= 0) {
            throw new BadRequestException("Tenure months must be greater than 0");
        }
        
        request.setUser(user);
        return loanRequestRepository.save(request);
    }
    
    @Override
    public List<LoanRequest> getRequestsByUser(Long userId) {
        userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return loanRequestRepository.findByUserId(userId);
    }
    
    @Override
    public LoanRequest getById(Long id) {
        return loanRequestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan request not found"));
    }
    
    @Override
    public List<LoanRequest> getAllRequests() {
        return loanRequestRepository.findAll();
    }
}