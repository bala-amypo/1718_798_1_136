package com.example.demo.repository;

import com.example.demo.entity.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    
    // Custom query to match the required method name 'findByUserid'
    @Query("SELECT lr FROM LoanRequest lr WHERE lr.user.id = :userid")
    List<LoanRequest> findByUserid(@Param("userid") Long userid);
}