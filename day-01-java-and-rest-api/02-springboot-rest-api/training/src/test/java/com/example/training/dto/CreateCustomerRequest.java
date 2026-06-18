package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCustomerRequest {
	@JsonProperty("full_name")
	@NotBlank
	@Size(min = 3)
	@Size(max = 100)
    private String fullName;

	@NotBlank
	@Email
	private String email;

	@JsonProperty("phone_number")
	@NotNull
	@Size(min = 10)
	private String phoneNumber;
}