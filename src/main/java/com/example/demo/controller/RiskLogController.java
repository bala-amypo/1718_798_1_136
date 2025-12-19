package com.example.demo.controller;

import com.example.demo.dto.RiskAssessmentLogDto;
import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.service.RiskAssessmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/risk-logs")
@Tag(name = "RiskLog", description = "Risk Assessment log endpoints")
public class RiskLogController {
    
    private final RiskAssessmentService riskAssessmentService;
    
    public RiskLogController(RiskAssessmentService riskAssessmentService) {
        this.riskAssessmentService = riskAssessmentService;
    }
    
    @GetMapping("/{loanRequestId}")
    public ResponseEntity<List<RiskAssessmentLogDto>> getByLoanRequestId(@PathVariable Long loanRequestId) {
        // Note: getByLoanRequestId returns a single log, but keeping as list for consistency
        RiskAssessmentLog log = riskAssessmentService.getByLoanRequestId(loanRequestId);
        
        List<RiskAssessmentLogDto> dtos = List.of(new RiskAssessmentLogDto(
                log.getId(),
                log.getLoanRequestId(),
                log.getDtiRatio(),
                log.getCreditCheckStatus(),
                log.getTimestamp()
        ));
        
        return ResponseEntity.ok(dtos);
    }
}