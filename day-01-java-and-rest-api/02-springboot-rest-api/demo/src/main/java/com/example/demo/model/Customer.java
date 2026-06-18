package com.example.demo.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Customer {
	private Long id;
	private String fullName;
	private String email;
	private String phoneNumber;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;

}
