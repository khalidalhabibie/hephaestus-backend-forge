package com.example.dbbackend.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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
