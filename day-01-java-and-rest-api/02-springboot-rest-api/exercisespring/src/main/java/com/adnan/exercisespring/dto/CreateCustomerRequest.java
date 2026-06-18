package com.adnan.exercisespring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {
  @JsonProperty("full_name")
  @NotBlank
  @Size(min = 3, max = 100)
  private String fullName;

  @NotBlank
  @Email
  private String email;

  @JsonProperty("phone_number")
  @NotBlank
  @Size(min = 10)
  private String phoneNumber;
}
