package com.example.dbbackend.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCustomerRequest {

    @NotBlank
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank
    @JsonProperty("nik")
    private String nik;

    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;

}
