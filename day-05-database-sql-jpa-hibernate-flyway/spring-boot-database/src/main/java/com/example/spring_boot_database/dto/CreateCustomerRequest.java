package com.example.spring_boot_database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateCustomerRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "full_name is required")
    private String fullName;

    @JsonProperty("nik")
    @NotBlank(message = "nik is required")
    private String nik;

    @JsonProperty("email")
    @NotBlank(message = "email is required")
    @Email
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "phone_number is required")
    private String phoneNumber;
}

// {
//   "full_name": "Budi Santoso",
//   "nik": "3173010101900001",
//   "email": "budi@mail.com",
//   "phone_number": "08123456789"
// }