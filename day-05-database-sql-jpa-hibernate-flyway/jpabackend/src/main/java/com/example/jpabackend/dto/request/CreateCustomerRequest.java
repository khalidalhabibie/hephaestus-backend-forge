package com.example.jpabackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    @NotBlank
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank
    @Min(16)
    @Max(16)
    @JsonProperty("nik")
    private String nik;

    @Email
    @JsonProperty("email")
    private String email;

    @NotBlank
    @Min(10)
    @Max(12)
    @JsonProperty("phone_number")
    private String phoneNumber;
}