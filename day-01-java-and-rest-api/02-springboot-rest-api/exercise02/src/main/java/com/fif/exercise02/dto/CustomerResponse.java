package com.fif.exercise02.dto;

import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonPropertyOrder({"id", "fullName", "email", "phoneNumber", "createdAt", "updatedAt"})
public class CustomerResponse {
    private String id;
    // @JsonProperty("full_name")
    private String fullName;
    private String email;
    // @JsonProperty("phone_number")
    private String phoneNumber;
    
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;


}
