package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.LoanRequest;

public interface LoanRequestService {

    LoanRequest submitLoanRequest(LoanRequest request);

    List<LoanRequest> getRequestsByUser(Long userId);

    LoanRequest getRequestById(Long id);

    List<LoanRequest> getAllRequests();
}
