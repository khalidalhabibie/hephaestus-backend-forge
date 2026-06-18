package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCustomerRequest {
	@JsonProperty("full_name")
	@NotBlank(message = "full_name is required")
	@Size(min = 3)
	@Size(max = 100)
    private String fullName;

	@JsonProperty("email")
	@NotBlank(message = "email is required")
	@Email(message = "email format is invalid")
	private String email;

	@JsonProperty("phone_number")
	@NotBlank(message = "phone_number is required")
	@Size(min = 10)
	private String phoneNumber;
}