package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerRequest {
    @NotBlank
    @Size(max = 100, min = 3)
    @JsonProperty("full_name")
    private String fullName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 10, max = 15)
    @JsonProperty("phone_number")
    private String phoneNumber;
}
