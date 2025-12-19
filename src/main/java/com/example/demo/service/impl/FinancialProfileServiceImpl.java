// package com.example.demo.service.impl;

// import com.example.demo.entity.FinancialProfile;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.repository.FinancialProfileRepository;
// import com.example.demo.service.FinancialProfileService;
// import org.springframework.stereotype.Service;

// @Service
// public class FinancialProfileServiceImpl implements FinancialProfileService {

//     private final FinancialProfileRepository repo;

//     public FinancialProfileServiceImpl(FinancialProfileRepository repo) {
//         this.repo = repo;
//     }

//     @Override
//     public FinancialProfile save(FinancialProfile profile) {
//         return repo.save(profile);
//     }

//     @Override
//     public FinancialProfile getById(Long id) {
//         return repo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Financial profile not found"));
//     }
// }
