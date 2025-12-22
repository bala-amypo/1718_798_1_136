package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FinancialProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialProfileServiceImpl implements FinancialProfileService {
    
    private final FinancialProfileRepository financialProfileRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public FinancialProfileServiceImpl(FinancialProfileRepository financialProfileRepository,
                                      UserRepository userRepository) {
        this.financialProfileRepository = financialProfileRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public FinancialProfile createOrUpdate(FinancialProfile profile) {
        // Check if user exists
        User user = userRepository.findById(profile.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Check if profile already exists for this user
        if (profile.getId() == null) {
            if (financialProfileRepository.findByUserId(user.getId()).isPresent()) {
                throw new BadRequestException("Financial profile already exists");
            }
        }
        
        // Simple validation
        if (profile.getCreditScore() < 300 || profile.getCreditScore() > 900) {
            throw new BadRequestException("Invalid creditScore");
        }
        
        if (profile.getMonthlyIncome() <= 0) {
            throw new BadRequestException("Monthly income must be positive");
        }
        
        profile.setUser(user);
        return financialProfileRepository.save(profile);
    }
    
    @Override
    public FinancialProfile getByUserId(Long userId) {
        return financialProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found"));
    }
}