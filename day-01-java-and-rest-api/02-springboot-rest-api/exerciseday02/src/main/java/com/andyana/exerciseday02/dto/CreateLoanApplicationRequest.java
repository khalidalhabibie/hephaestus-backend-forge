package com.andyana.exerciseday02.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLoanApplicationRequest {
    
    @NotNull(message = "Tolong isi ID customernya")
    @JsonProperty("customer_id")
    private Long customerId;
    
    @NotNull(message = "Tolong isi Loan Amountnya")
    @Min(value = 1, message = "Loan amount lebih dari 0")
    @JsonProperty("loan_amount")
    private Long loanAmount;
    
    @NotNull(message = "Tolong isi tenornya berapa bulan")
    @Min(value = 1, message = "Harus lebih dari 0")
    @JsonProperty("tenor_month")
    private Integer tenorMonth;
    
    @NotBlank(message = "Tolong isi tujuan pinjaman")
    private String purpose;
}
