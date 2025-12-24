package com.example.demo.service.impl;

import com.example.demo.entity.LoanRequest;
import com.example.demo.repository.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.service.LoanRequestService;
import java.util.List;

public class LoanRequestServiceImpl implements LoanRequestService {
    private final LoanRequestRepository loanRepo;
    private final UserRepository userRepo;

    public LoanRequestServiceImpl(LoanRequestRepository loanRepo, UserRepository userRepo) {
        this.loanRepo = loanRepo;
        this.userRepo = userRepo;
    }

    @Override
    public LoanRequest submitRequest(LoanRequest request) {
        if (request.getRequestedAmount() == null || request.getRequestedAmount() <= 0) {
            throw new BadRequestException("Requested amount");
        }
        return loanRepo.save(request);
    }

    @Override
    public List<LoanRequest> getRequestsByUser(Long userId) {
        return loanRepo.findByUserId(userId);
    }

    @Override
    public LoanRequest getById(Long id) {
        return loanRepo.findById(id).orElse(null);
    }
}