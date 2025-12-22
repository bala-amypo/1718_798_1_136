package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.entity.LoanRequest;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.service.LoanRequestService;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

    private final LoanRequestRepository repo;

    public LoanRequestServiceImpl(LoanRequestRepository repo) {
        this.repo = repo;
    }

    public LoanRequest save(LoanRequest request) {
        request.setStatus("PENDING");
        return repo.save(request);
    }

    public LoanRequest getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<LoanRequest> getAll() {
        return repo.findAll();
    }
}
