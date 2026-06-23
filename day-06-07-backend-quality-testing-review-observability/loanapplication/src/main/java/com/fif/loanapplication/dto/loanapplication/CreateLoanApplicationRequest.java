package com.fif.loanapplication.dto.loanapplication;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fif.loanapplication.dto.common.BaseDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({

})
public class CreateLoanApplicationRequest extends BaseDto {
    @NotNull(message = "Customer wajib diisi")
    @JsonProperty("customer_uid")
    UUID customerUid;

    @NotNull(message = "Loan amount wajib diisi")
    @DecimalMin(value = "1.00", message = "Loan Amount harus lebih besar dari 0!")
    @JsonProperty("loan_amount")
    BigDecimal loanAmount;

    @NotNull(message = "Tenor month wajib diisi")
    @Min(value = 1, message = "Tenor month minimal 1 bulan")
    @JsonProperty("tenor_month")
    Integer tenorMonth;

    @NotBlank(message = "Purpose wajib diisi")
    String purpose;
}
