package com.example.demo.controller;

import com.example.demo.entity.LoanRequest;
import com.example.demo.repository.LoanRequestRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-request")
public class LoanRequestController {

    private final LoanRequestRepository repo;

    public LoanRequestController(LoanRequestRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/create")
    public LoanRequest create(@RequestBody LoanRequest req) {
        return repo.save(req);
    }
}
