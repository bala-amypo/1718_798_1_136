package com.example.demo.repository;

import com.example.demo.entity.FinancialProfile;
import java.util.Optional;

public interface FinancialProfileRepository {
    Optional<FinancialProfile> findByUserId(Long userId);
}
