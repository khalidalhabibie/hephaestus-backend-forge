// Terima input JSON create customer + validasi (@NotBlank, @Email, dll).

package com.example.training.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCustomerRequest {

    @NotBlank(message = "full_name is required")
    @Size(max = 100, message = "full_name max 100 characters")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "nik is required")
    @Size(min = 16, max = 16, message = "nik max 16 characters")
    @JsonProperty("nik")
    private String nik;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    @Size(max = 150, message = "email max 150 characters")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "phone_number is required")
    @Size(min = 11, max = 13, message = "phone_number max 13 characters")
    @JsonProperty("phone_number")
    private String phoneNumber;
}