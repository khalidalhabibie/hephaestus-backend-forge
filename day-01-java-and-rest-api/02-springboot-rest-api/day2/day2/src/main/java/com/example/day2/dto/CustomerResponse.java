package com.example.day2.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerResponse {
    private Long id;
	private String fullName;
	private String email;
	private String phoneNumber;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
}
