package com.example.jpabackend.dto.request;

import com.example.jpabackend.enums.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLoanStatusRequest {
    private LoanStatus status;
}