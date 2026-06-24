package com.example.demoSpringbootDatabase.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import com.example.demoSpringbootDatabase.dto.CreateCustomerRequest;

@Data

public class CreateCustomerRequest {
    @NotBlank(message = "full_name is required")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "nik is required")
    @Size(min = 16, max = 16, message = "nik must be 16 digits")
    private String nik;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "phone_number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
}