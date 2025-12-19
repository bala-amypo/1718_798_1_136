package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FinancialProfileService;
import org.springframework.stereotype.Service;

@Service
public class FinancialProfileServiceImpl implements FinancialProfileService {
    
    private final FinancialProfileRepository profileRepository;
    private final UserRepository userRepository;
    
    public FinancialProfileServiceImpl(FinancialProfileRepository profileRepository, 
                                      UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public FinancialProfile createOrUpdate(FinancialProfile profile) {
        // Validate credit score
        if (profile.getCreditScore() < 300 || profile.getCreditScore() > 900) {
            throw new BadRequestException("creditScore must be between 300 and 900");
        }
        
        // Validate monthly income
        if (profile.getMonthlyIncome() <= 0) {
            throw new BadRequestException("monthlyIncome must be greater than 0");
        }
        
        // Check if user exists
        User user = userRepository.findById(profile.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Check for duplicate profile
        if (profile.getId() == null) {
            if (profileRepository.findByUserId(user.getId()).isPresent()) {
                throw new BadRequestException("Financial profile already exists");
            }
        }
        
        profile.setUser(user);
        return profileRepository.save(profile);
    }
    
    @Override
    public FinancialProfile getByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found"));
    }
}