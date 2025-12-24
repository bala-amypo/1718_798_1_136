package com.example.demo.service.impl;

import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanRequestServiceImpl(LoanRequestRepository loanRequestRepository, 
                                 UserRepository userRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LoanRequest submitRequest(LoanRequest loanRequest) {
        // Validate request
        if (loanRequest.getRequestedAmount() == null || loanRequest.getRequestedAmount() <= 0) {
            throw new BadRequestException("Requested amount must be positive");
        }
        if (loanRequest.getTenureMonths() == null || loanRequest.getTenureMonths() <= 0) {
            throw new BadRequestException("Tenure must be positive");
        }

        // Validate user exists
        User user = userRepository.findById(loanRequest.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        loanRequest.setUser(user);
        loanRequest.setStatus(LoanRequest.Status.PENDING.name());
        loanRequest.setSubmittedAt(LocalDateTime.now());

        return loanRequestRepository.save(loanRequest);
    }

    @Override
    public LoanRequest getById(Long id) {
        return loanRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found with id: " + id));
    }

    @Override
    public List<LoanRequest> getRequestsByUser(Long userId) {
        return loanRequestRepository.findByUserId(userId);
    }

    @Override
    public List<LoanRequest> getAll() {
        return loanRequestRepository.findAll();
    }

    @Override
    public LoanRequest updateStatus(Long id, String status) {
        LoanRequest loanRequest = getById(id);
        loanRequest.setStatus(status);
        return loanRequestRepository.save(loanRequest);
    }
}