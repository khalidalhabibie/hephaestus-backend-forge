package com.example.demo_day2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCustomerRequest {

    @NotBlank
    @Size(max = 100)
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    @Size(min = 10)
    @JsonProperty("phone_number")
    private String phoneNumber;

}
