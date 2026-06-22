package com.example.training_2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchCustomerRequest {

    @Size(max = 255, message = "Full name must not exceed 255 characters")
    @JsonProperty("full_name")
    private String fullName;

    @Pattern(regexp = "^\\d{16}$", message = "NIK must consist of exactly 16 digits")
    private String nik;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @JsonProperty("phone_number")
    private String phoneNumber;
}