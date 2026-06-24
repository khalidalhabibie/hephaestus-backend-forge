package com.example.jpabackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    @NotBlank
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank
    @Size(min = 16, max = 16)
    @JsonProperty("nik")
    private String nik;

    @Email
    @JsonProperty("email")
    private String email;

    @NotBlank
    @Size(min = 10, max = 12)
    @JsonProperty("phone_number")
    private String phoneNumber;
}