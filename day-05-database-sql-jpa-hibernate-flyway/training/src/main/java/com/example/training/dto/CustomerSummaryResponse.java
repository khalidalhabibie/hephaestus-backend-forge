package com.example.training.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSummaryResponse {

    private UUID id;

    @JsonProperty("full_name")
    private String fullName;

    private String nik;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;
}
