package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.EligibilityResult;

public interface EligibilityResultRepository {
    Optional<EligibilityResult> findByLoanRequestId(Long id);
    EligibilityResult save(EligibilityResult er);
}
