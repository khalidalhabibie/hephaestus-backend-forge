package com.example.demoSpringbootDatabase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RepaymentScheduleResponse {
    private Long id;
    @JsonProperty("installment_number")
    private Integer installmentNumber;
    @JsonProperty("due_date")
    private LocalDate dueDate;
    @JsonProperty("principal_amount")
    private Long principalAmount;
    @JsonProperty("interest_amount")
    private Long interestAmount;
    @JsonProperty("total_amount")
    private Long totalAmount;
    private String status;
}
