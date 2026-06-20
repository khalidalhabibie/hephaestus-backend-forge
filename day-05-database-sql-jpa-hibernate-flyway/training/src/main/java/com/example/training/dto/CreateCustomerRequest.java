package com.example.training.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    @NotBlank
    private String full_name;

    @NotBlank
    @Pattern(regexp = "\\d{16}")
    private String nik;

    @NotBlank
    private String email;
    private String phone_number;
}

// fullname
// nik
// email
// phone_number