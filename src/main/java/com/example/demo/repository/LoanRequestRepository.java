package com.example.demo.repository;
import com.example.demo.entity.LoanRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.LoanRequest;

public interface LoanRequestRepository {
    LoanRequest save(LoanRequest lr);
    Optional<LoanRequest> findById(Long id);
    List<LoanRequest> findByUserId(Long userId);
    List<LoanRequest> findAll();
}

