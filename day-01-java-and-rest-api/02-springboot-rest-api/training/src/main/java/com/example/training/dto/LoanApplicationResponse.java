package com.example.training.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "id",
    "customer_id",
    "loan_amount",
    "tenor_month",
    "purpose",
    "status",
    "created_at",
    "updated_at"
})
public class LoanApplicationResponse {
    private Long id;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("loan_amount")
    private Long loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private String status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
	
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
