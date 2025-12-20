package com.example.demo.repository;

import com.example.demo.entity.FinancialProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialProfileRepository extends JpaRepository<FinancialProfile, Long> {
    
    // Custom query to match the required method name 'findByUserld' (with lowercase 'L')
    @Query("SELECT fp FROM FinancialProfile fp WHERE fp.user.id = :userld")
    FinancialProfile findByUserld(@Param("userld") Long userld);
}