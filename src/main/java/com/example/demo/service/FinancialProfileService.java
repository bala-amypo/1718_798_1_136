package com.example.demo.service;

import com.example.demo.entity.FinancialProfile;

public interface FinancialProfileService {
    FinancialProfile save(FinancialProfile profile);
    FinancialProfile getByUserId(Long userId);
}
