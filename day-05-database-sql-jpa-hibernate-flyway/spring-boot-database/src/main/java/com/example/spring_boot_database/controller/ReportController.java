package com.example.spring_boot_database.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot_database.dto.ApiResponse;
import com.example.spring_boot_database.dto.LoanApplicationReportProjection;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/loan-applications")
    public ApiResponse<List<LoanApplicationReportProjection>> getLoanApplicationReport(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ApiResponse.success(
                reportService.getLoanApplicationReport(status, startDate, endDate),
                "Loan application report retrieved successfully"
        );
    }
}