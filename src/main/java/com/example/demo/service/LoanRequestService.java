package com.example.demo.service;

import com.example.demo.entity.LoanRequest;

public interface LoanRequestService {

    LoanRequest createLoanRequest(LoanRequest request);

    LoanRequest getLoanRequestById(Long id);
}
