package com.example.demo.service;

import com.example.demo.entity.LoanRequest;
import java.util.List;

public interface LoanRequestService {

    LoanRequest create(LoanRequest request);

    LoanRequest getById(Long id);

    List<LoanRequest> getByUserId(Long userId);
}
