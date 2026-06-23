package com.fif.loanapplication.dto.loanapplication;

import com.fif.loanapplication.entity.enums.LoanStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLoanStatusRequest {
    @NotBlank(message = "Status wajib diisi")
    LoanStatus status;
}
