// package com.example.demo.controller;

// import com.example.demo.entity.RiskAssessmentLog;
// import com.example.demo.service.RiskAssessmentLogService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/risk-log")
// public class RiskLogController {

//     private final RiskAssessmentLogService service;

//     public RiskLogController(RiskAssessmentLogService service) {
//         this.service = service;
//     }

//     @PostMapping("/add")
//     public RiskAssessmentLog addLog(@RequestBody RiskAssessmentLog log) {
//         return service.save(log);
//     }

//     @GetMapping("/all")
//     public List<RiskAssessmentLog> getAllLogs() {
//         return service.getAll();
//     }
// }
