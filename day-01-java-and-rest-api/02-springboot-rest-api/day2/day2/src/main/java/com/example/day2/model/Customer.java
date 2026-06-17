package com.example.day2.model;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
@Deprecated
public class Customer {
    private Long id;
	private String fullName;
	private String email;
	private String phoneNumber;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
}
