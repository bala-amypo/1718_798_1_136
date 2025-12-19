// package com.example.demo.controller;

// import com.example.demo.entity.LoanRequest;
// import com.example.demo.service.LoanRequestService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/loan")
// public class LoanRequestController {

//     private final LoanRequestService service;

//     public LoanRequestController(LoanRequestService service) {
//         this.service = service;
//     }

//     @PostMapping("/apply")
//     public LoanRequest applyLoan(@RequestBody LoanRequest loanRequest) {
//         return service.save(loanRequest);
//     }

//     @GetMapping("/all")
//     public List<LoanRequest> getAllLoans() {
//         return service.getAll();
//     }

//     @GetMapping("/{id}")
//     public LoanRequest getLoanById(@PathVariable Long id) {
//         return service.getById(id);
//     }
// }
