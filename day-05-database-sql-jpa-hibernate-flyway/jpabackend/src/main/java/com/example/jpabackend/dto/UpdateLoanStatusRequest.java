package com.example.jpabackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Setter
// @Getter
// @AllArgsConstructor
// @NoArgsConstructor
public class UpdateLoanStatusRequest {

    @NotBlank
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateLoanStatusRequest(@NotBlank String status) {
        this.status = status;
    }

    public UpdateLoanStatusRequest() {
    }

}
