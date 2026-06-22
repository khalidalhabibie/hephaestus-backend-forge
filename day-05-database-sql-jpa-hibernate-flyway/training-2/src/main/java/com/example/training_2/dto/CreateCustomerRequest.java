package com.example.training_2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCustomerRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 255, message = "Full name must not exceed 255 characters")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "NIK is required")
    @Size(min = 16, max = 16, message = "NIK must be 16 digits")
    @Pattern(regexp = "\\d+", message = "NIK must contain only numbers")
    private String nik;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @JsonProperty("phone_number")
    private String phoneNumber;
}