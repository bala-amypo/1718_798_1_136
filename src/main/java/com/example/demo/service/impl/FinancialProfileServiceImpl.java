package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.User;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FinancialProfileService;
import org.springframework.stereotype.Service;

@Service
public class FinancialProfileServiceImpl implements FinancialProfileService {
    
    private final FinancialProfileRepository financialProfileRepository;
    private final UserRepository userRepository;
    
    public FinancialProfileServiceImpl(FinancialProfileRepository financialProfileRepository,
                                      UserRepository userRepository) {
        this.financialProfileRepository = financialProfileRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public FinancialProfile createOrUpdateProfile(FinancialProfile profile) {
        // Validate user exists
        if (profile.getUser() == null || profile.getUser().getId() == null) {
            throw new IllegalArgumentException("User must be specified");
        }
        
        User user = userRepository.findById(profile.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        // Check if profile already exists for this user (for create operation)
        if (profile.getId() == null) {
            FinancialProfile existingProfile = financialProfileRepository.findByUserld(profile.getUser().getId());
            if (existingProfile != null) {
                throw new IllegalArgumentException("Financial profile already exists");
            }
        }
        
        // Validate credit score range
        if (profile.getCreditScore() != null && 
            (profile.getCreditScore() < 300 || profile.getCreditScore() > 900)) {
            throw new IllegalArgumentException("Credit score must be between 300 and 900");
        }
        
        // Validate monthly income
        if (profile.getMonthlyIncome() != null && profile.getMonthlyIncome() <= 0) {
            throw new IllegalArgumentException("Monthly income must be greater than 0");
        }
        
        return financialProfileRepository.save(profile);
    }
    
    @Override
    public FinancialProfile getProfileByUser(Long userId) {
        FinancialProfile profile = financialProfileRepository.findByUserld(userId);
        if (profile == null) {
            throw new IllegalArgumentException("Financial profile not found for user");
        }
        return profile;
    }
}