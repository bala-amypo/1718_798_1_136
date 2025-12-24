package com.example.demo.service;

import com.example.demo.entity.FinancialProfile;

public interface FinancialProfileService {
    FinancialProfile createOrUpdate(FinancialProfile fp);
    FinancialProfile getByUserId(Long userId);
}
