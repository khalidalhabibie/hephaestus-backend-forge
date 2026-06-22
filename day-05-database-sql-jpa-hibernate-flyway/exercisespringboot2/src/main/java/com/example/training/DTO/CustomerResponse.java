// Format JSON response data customer lengkap.

package com.example.training.DTO;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
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

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
}