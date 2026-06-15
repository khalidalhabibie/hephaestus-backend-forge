package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCustomerRequest {

	@JsonProperty("full_name")
	@NotBlank
	@Size(max = 100, min = 3)
	private String fullName;
	@NotBlank
	@Email
	@JsonProperty("email")
	private String email;
	@NotBlank
	@Size(max = 10)
	@JsonProperty("phone_number")
	private String phoneNumber;

	public String getFullName() {
		return fullName;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


}


