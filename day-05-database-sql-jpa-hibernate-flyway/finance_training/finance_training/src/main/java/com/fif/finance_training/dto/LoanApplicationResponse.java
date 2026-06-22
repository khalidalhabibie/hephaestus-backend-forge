package com.fif.finance_training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponse {

    // Ini field yang hilang di file lama kamu, wajib ada agar error id(Long) hilang
    private Long id;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    private String purpose;
    
    private String status;

    private CustomerDetail customer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerDetail {
        
        private Long id;

        @JsonProperty("full_name")
        private String fullName;

        private String nik;
        
        private String email;
    }
}