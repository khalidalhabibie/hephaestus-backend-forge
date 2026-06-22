package com.example.training_2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.training_2.dto.WebResponse;
import com.example.training_2.service.LoanApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @GetMapping
    public WebResponse<List<LoanApplicationResponse>> getAll() {
        return WebResponse.success(null, null);
    }

}
