package com.example.demo.repository;

import com.example.demo.entity.RiskAssessmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RiskAssessmentLogRepository extends JpaRepository<RiskAssessmentLog, Long> {
    @Query("SELECT ral FROM RiskAssessmentLog ral WHERE ral.loanRequestId = :loanRequestid")
    List<RiskAssessmentLog> findByLoanRequestid(@Param("loanRequestid") Long loanRequestid);
}