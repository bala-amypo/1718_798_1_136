package com.example.demo.service;

import com.example.demo.entity.FinancialProfile;

public interface FinancialProfileService {
    
    /**
     * Create or update financial profile
     * @throws IllegalArgumentException if profile already exists with message "Financial profile already exists"
     */
    FinancialProfile createOrUpdateProfile(FinancialProfile profile);
    
    /**
     * Get financial profile by user ID
     * @throws IllegalArgumentException if not found
     */
    FinancialProfile getProfileByUser(Long userId);
}