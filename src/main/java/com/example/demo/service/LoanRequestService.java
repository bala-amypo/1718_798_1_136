package com.example.demo.service;

import com.example.demo.entity.LoanRequest;
import java.util.List;

public interface LoanRequestService {
    
    /**
     * Submit a new loan request
     * @throws IllegalArgumentException if requestedAmount <= 0 with message containing "Requested amount"
     */
    LoanRequest submitLoanRequest(LoanRequest request);
    
    /**
     * Get loan requests by user ID
     */
    List<LoanRequest> getRequestsByUser(Long userId);
    
    /**
     * Get loan request by ID
     * @throws IllegalArgumentException if not found
     */
    LoanRequest getRequestById(Long id);
    
    /**
     * Get all loan requests
     */
    List<LoanRequest> getAllRequests();
}