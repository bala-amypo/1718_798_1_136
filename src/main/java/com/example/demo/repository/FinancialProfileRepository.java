package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.FinancialProfile;

public interface FinancialProfileRepository extends JpaRepository<FinancialProfile, Long> {
    FinancialProfile findByUserId(Long userId);
}
