package com.fif.training.exercisespringboot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({
		"full_name",
		"email",
		"phone_number"
})
public class CreateCustomerRequest {
	@JsonProperty("full_name")
	@NotBlank(message = "Fullname tidak boleh kosong")
	@Size(max = 100, message = "Maksimal 100 karakter")
	public String fullName;

	@NotBlank(message = "Email tidak boleh kosong")
	@Email
	public String email;

	@JsonProperty("phone_number")
	@NotBlank(message = "phone number tidak boleh kosong")
	@Min(value = 10, message = "Minimal 10 karakter")
	public String phoneNumber;
}
