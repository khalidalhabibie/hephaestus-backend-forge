package com.fif.training.exercisespringboot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
	public String fullName;

	public String email;

	@JsonProperty("phone_number")
	public String phoneNumber;
}
