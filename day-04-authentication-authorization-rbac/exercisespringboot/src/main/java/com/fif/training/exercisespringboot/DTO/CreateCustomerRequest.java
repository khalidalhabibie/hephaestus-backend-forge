package com.fif.training.exercisespringboot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({
		"id",
		"full_name",
		"email",
		"phone_number",
		"created_at",
		"updated_at"
})
public class CreateCustomerRequest {

	@JsonProperty("full_name")
	@NotBlank(message = "Fullname wajib di isi")
	@Size(min = 3, max = 100, message = "Fullname harus 3 sampai 100 karakter")
	public String fullName;

	@NotBlank(message = "Email wajib di isi")
	@Email(message = "Email harus valid!")
	public String email;

	@JsonProperty("phone_number")
	@NotBlank(message = "Phone Number wajib di isi")
	@Size(min = 10, max = 15, message = "Phone number harus 10 sampai 15 karakter")
	@Pattern(regexp = "^[0-9]+$", message = "Phone number hanya boleh angka")
	public String phoneNumber;
}
