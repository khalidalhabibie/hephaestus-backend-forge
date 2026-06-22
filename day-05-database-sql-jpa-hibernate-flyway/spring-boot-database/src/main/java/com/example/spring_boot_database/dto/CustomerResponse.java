package com.example.spring_boot_database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CustomerResponse {
    @JsonProperty("id")
	private Long id;

	@JsonProperty("full_name")
	private String fullName;

    @JsonProperty("nik")
	private String nik;

	@JsonProperty("email")
	private String email;

    @JsonProperty("phone_number")
	private String phoneNumber;
}

    // "customer": {
    //   "id": 1,
    //   "full_name": "Budi Santoso",
    //   "nik": "3173010101900001",
    //   "email": "budi@mail.com"