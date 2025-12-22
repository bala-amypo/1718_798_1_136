package com.example.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

    @Override
    public LoanRequest submitLoanRequest(LoanRequest request) {
        return request;
    }

    @Override
    public List<LoanRequest> getRequestsByUser(Long userId) {
        return List.of();
    }

    @Override
    public LoanRequest getRequestById(Long id) {
        return null;
    }

    @Override
    public List<LoanRequest> getAllRequests() {
        return List.of();
    }
}
