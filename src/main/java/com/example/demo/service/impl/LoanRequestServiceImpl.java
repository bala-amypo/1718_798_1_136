package com.example.demo.service.impl;

import com.example.demo.entity.LoanRequest;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.service.LoanRequestService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
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

        if (request.getSubmittedAt() == null) {
            request.setSubmittedAt(LocalDateTime.now());
        }
        if (request.getStatus() == null) {
            request.setStatus(LoanRequest.Status.PENDING.name());
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