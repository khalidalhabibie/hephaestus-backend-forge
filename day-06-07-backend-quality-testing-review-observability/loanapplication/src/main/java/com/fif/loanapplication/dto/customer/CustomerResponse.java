package com.fif.loanapplication.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fif.loanapplication.dto.common.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonPropertyOrder({
        "uid",
        "nik",
        "full_name",
        "email",
        "phone_number",
        "created_at",
        "updated_at"
})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerResponse extends BaseDto {

    String nik;
    @JsonProperty("full_name")
    String fullName;

    String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number tidak boleh kosong!")
    String phoneNumber;
}
