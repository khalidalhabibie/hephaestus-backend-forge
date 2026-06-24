package com.example.jpabackend.dto.request;

import com.example.jpabackend.enums.RepaymentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRepaymentScheduleStatusRequest {
    @NotNull
    private RepaymentStatus status;
}
