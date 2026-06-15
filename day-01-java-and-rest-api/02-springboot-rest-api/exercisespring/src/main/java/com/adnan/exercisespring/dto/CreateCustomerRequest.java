package com.adnan.exercisespring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
