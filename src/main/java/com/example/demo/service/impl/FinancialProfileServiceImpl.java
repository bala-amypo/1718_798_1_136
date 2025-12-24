package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.service.FinancialProfileService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class FinancialProfileServiceImpl implements FinancialProfileService {
    private final FinancialProfileRepository profileRepo;
    private final UserRepository userRepo;

    public FinancialProfileServiceImpl(FinancialProfileRepository profileRepo, UserRepository userRepo) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
    }

    @Override
    public FinancialProfile createOrUpdate(FinancialProfile profile) {
        if (profile.getCreditScore() != null && (profile.getCreditScore() < 300 || profile.getCreditScore() > 900)) {
            throw new BadRequestException("creditScore");
        }
        
        profile.setLastUpdatedAt(LocalDateTime.now());

        return profileRepo.findByUserId(profile.getUser().getId())
                .map(existing -> {
                    existing.setMonthlyIncome(profile.getMonthlyIncome());
                    existing.setMonthlyExpenses(profile.getMonthlyExpenses());
                    existing.setCreditScore(profile.getCreditScore());
                    existing.setSavingsBalance(profile.getSavingsBalance());
                    existing.setExistingLoanEmi(profile.getExistingLoanEmi());
                    existing.setLastUpdatedAt(LocalDateTime.now());
                    return profileRepo.save(existing);
                }).orElseGet(() -> profileRepo.save(profile));
    }

    @Override
    public FinancialProfile getByUserId(Long userId) {
        return profileRepo.findByUserId(userId).orElse(null);
    }
}