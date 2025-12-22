package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoanDtos;
import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin
public class LoanRequestController {

    @Autowired
    private LoanRequestService loanRequestService;


    @PostMapping("/submit")
    public LoanDtos.LoanRequestDto submitLoanRequest(
            @RequestBody LoanRequest request) {

        LoanRequest savedRequest = loanRequestService.submitLoanRequest(request);

        LoanDtos.LoanRequestDto dto = new LoanDtos.LoanRequestDto();
        dto.setRequestedAmount(savedRequest.getRequestedAmount());
        dto.setTenureMonths(savedRequest.getTenureMonths());
        dto.setStatus(savedRequest.getStatus());

        return dto;
    }

    @GetMapping("/user/{userId}")
    public List<LoanDtos.LoanRequestDto> getLoansByUser(
            @PathVariable Long userId) {

        List<LoanRequest> requests =
                loanRequestService.getRequestsByUser(userId);

        return requests.stream().map(request -> {
            LoanDtos.LoanRequestDto dto =
                    new LoanDtos.LoanRequestDto();

            dto.setRequestedAmount(request.getRequestedAmount());
            dto.setTenureMonths(request.getTenureMonths());
            dto.setStatus(request.getStatus());

            return dto;
        }).toList();
    }

    @GetMapping("/{id}")
    public LoanDtos.LoanRequestDto getLoanById(
            @PathVariable Long id) {

        LoanRequest request =
                loanRequestService.getRequestById(id);

        LoanDtos.LoanRequestDto dto =
                new LoanDtos.LoanRequestDto();

        dto.setRequestedAmount(request.getRequestedAmount());
        dto.setTenureMonths(request.getTenureMonths());
        dto.setStatus(request.getStatus());

        return dto;
    }

    @GetMapping("/all")
    public List<LoanDtos.LoanRequestDto> getAllLoans() {

        List<LoanRequest> requests =
                loanRequestService.getAllRequests();

        return requests.stream().map(request -> {
            LoanDtos.LoanRequestDto dto =
                    new LoanDtos.LoanRequestDto();

            dto.setRequestedAmount(request.getRequestedAmount());
            dto.setTenureMonths(request.getTenureMonths());
            dto.setStatus(request.getStatus());

            return dto;
        }).toList();
    }
}
