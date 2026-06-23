package com.example.spring_boot_database.dto;

import com.example.spring_boot_database.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateLoanStatusRequest {
    @JsonProperty("status")
    @NotNull(message = "status is required")
    private Status status;
}
