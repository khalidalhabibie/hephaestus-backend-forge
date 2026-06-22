package com.fif.finance_training.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLoanStatusRequest {
    // Tidak butuh @JsonProperty karena namanya sama
    @NotBlank(message = "status is required")
    private String status;
}
