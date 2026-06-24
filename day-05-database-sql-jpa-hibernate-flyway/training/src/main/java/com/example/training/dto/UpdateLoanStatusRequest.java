package com.example.training.dto;

import com.example.training.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateLoanStatusRequest {

    @NotNull(message = "status is required")
    @JsonProperty("status")
    private LoanStatus status;
}
