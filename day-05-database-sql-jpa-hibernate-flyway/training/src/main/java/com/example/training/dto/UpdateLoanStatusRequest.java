package com.example.training.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLoanStatusRequest {

    @NotBlank
    private String status;
}
