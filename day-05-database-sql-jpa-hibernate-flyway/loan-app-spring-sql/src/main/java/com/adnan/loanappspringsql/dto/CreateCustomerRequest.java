package com.adnan.loanappspringsql.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCustomerRequest {
  @JsonProperty("full_name")
  @NotBlank(message = "full_name is required")
  private String fullName;

  @NotBlank(message = "nik is required")
  private String nik;

  @Email(message = "email is invalid")
  @NotBlank(message = "email is required")
  private String email;

  @JsonProperty("phone_number")
  @NotBlank(message = "phone_number is required")
  private String phoneNumber;
}