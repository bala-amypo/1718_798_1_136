package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.EligibilityResult;

public interface EligibilityResultRepository extends JpaRepository<EligibilityResult, Long> {
    EligibilityResult findByLoanRequestId(Long loanRequestId);
}
