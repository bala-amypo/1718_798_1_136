package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.service.FinancialProfileService;

@Service
public class FinancialProfileServiceImpl implements FinancialProfileService {

    private final FinancialProfileRepository repo;

    public FinancialProfileServiceImpl(FinancialProfileRepository repo) {
        this.repo = repo;
    }

    public FinancialProfile save(FinancialProfile profile) {
        return repo.save(profile);
    }

    public FinancialProfile getByUserId(Long userId) {
        return repo.findByUserId(userId);
    }
}
