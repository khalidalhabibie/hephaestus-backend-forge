// Terima input JSON update status loan.

package com.example.training.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLoanStatusRequest {

    @NotBlank(message = "status is required")
    @JsonProperty("status")
    private String status;
}