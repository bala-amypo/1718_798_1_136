package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.LoanRequest;

public interface LoanRequestService {
    LoanRequest save(LoanRequest request);
    LoanRequest getById(Long id);
    List<LoanRequest> getAll();
}
