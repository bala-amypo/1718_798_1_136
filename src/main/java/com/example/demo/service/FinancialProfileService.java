package com.example.demo.service;

import com.example.demo.entity.FinancialProfile;

public interface FinancialProfileService {
    FinancialProfile createOrUpdate(FinancialProfile financialProfile);
    FinancialProfile getByUserId(Long userId);
}