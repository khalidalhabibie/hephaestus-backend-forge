package com.example.jpabackend.dto.request;

import com.example.jpabackend.enums.LoanStatus;
import lombok.Data;

@Data
public class UpdateLoanStatusRequest {
    private LoanStatus status;
}