package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    @NotBlank
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank
    @Pattern(regexp = "\\d{16}")
    private String nik;

    @NotBlank
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;
}

// fullname
// nik
// email
// phone_number