package com.fif.loanapplication.dto.loanapplication;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fif.loanapplication.dto.common.BaseDto;
import com.fif.loanapplication.dto.customer.CustomerSummaryResponse;
import com.fif.loanapplication.entity.enums.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({

        "uid",
        "loan_amount",
        "tenor_month",
        "purpose",
        "status",
        "created_at",
        "updated_at",
        "customer"

})
public class LoanApplicationResponse extends BaseDto {

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("tenor_month")
    private Integer tenorMonth;

    private String purpose;
    private LoanStatus status;
    private CustomerSummaryResponse customer;
}
