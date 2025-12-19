// package com.example.demo.controller;

// import com.example.demo.entity.FinancialProfile;
// import com.example.demo.service.FinancialProfileService;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/financial-profile")
// public class FinancialProfileController {

//     private final FinancialProfileService service;

//     public FinancialProfileController(FinancialProfileService service) {
//         this.service = service;
//     }

//     @PostMapping("/add")
//     public FinancialProfile addProfile(@RequestBody FinancialProfile profile) {
//         return service.save(profile);
//     }

//     @GetMapping("/{id}")
//     public FinancialProfile getProfile(@PathVariable Long id) {
//         return service.getById(id);
//     }
// }
