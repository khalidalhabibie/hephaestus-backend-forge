package com.example.dbbackend.loanapplication.dto;

import com.example.dbbackend.loanapplication.entity.LoanApplicationStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLoanStatusRequest {

    @NotBlank
    private LoanApplicationStatus status;
}
