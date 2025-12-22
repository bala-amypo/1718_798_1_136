package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.FinancialProfile;

public interface FinancialProfileRepository {
    Optional<FinancialProfile> findByUserId(Long userId);
    FinancialProfile save(FinancialProfile fp);
}
