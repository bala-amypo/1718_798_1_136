package com.example.demo.repository;

import com.example.demo.entity.FinancialProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialProfileRepository extends JpaRepository<FinancialProfile, Long> {
    
    // Must be exactly this name for tests (findByUserld - with lowercase 'L')
    FinancialProfile findByUserld(Long userld);
}