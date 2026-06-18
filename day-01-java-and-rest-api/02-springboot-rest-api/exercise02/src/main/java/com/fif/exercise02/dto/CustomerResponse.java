package com.fif.exercise02.dto;

import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerResponse {
    private Long id;
    // @JsonProperty("full_name")
    private String fullName;
    private String email;
    // @JsonProperty("phone_number")
    private String phoneNumber;

}
