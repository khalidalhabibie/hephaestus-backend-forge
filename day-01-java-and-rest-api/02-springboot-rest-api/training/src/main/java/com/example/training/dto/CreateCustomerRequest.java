package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class CreateCustomerRequest {
    @Getter
    @Setter
    @JsonProperty("full_name")
    private String fullName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @JsonProperty("phone_number")
    private String phoneNumber;
}
