package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "id",
    "full_name",
    "email",
    "phone_number"
})

public class PatchCustomerRequest {
	private Long id;
	@JsonProperty("full_name")
	private String fullName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("phone_number")
	private String phoneNumber;
}