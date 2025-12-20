package com.example.demo.repository;

import com.example.demo.entity.EligibilityResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EligibilityResultRepository extends JpaRepository<EligibilityResult, Long> {
    @Query("SELECT er FROM EligibilityResult er WHERE er.loanRequest.id = :loanRequestid")
    EligibilityResult findByLoanRequestid(@Param("loanRequestid") Long loanRequestid);
}