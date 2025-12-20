package com.example.demo.repository;

import com.example.demo.entity.EligibilityResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EligibilityResultRepository extends JpaRepository<EligibilityResult, Long> {
    
    // Must be exactly this name for tests
    EligibilityResult findByLoanRequestid(Long loanRequestid);
}