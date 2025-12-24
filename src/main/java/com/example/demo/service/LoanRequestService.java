package com.example.demo.service;

import com.example.demo.entity.LoanRequest;
import java.util.List;

public interface LoanRequestService {
    LoanRequest submitRequest(LoanRequest loanRequest);
    LoanRequest getById(Long id);
    List<LoanRequest> getRequestsByUser(Long userId);
    List<LoanRequest> getAll();
    LoanRequest updateStatus(Long id, String status);
}