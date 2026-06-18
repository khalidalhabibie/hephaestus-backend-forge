package com.fif.training.exercisespringboot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({
        "id",
        "full_name",
        "email",
        "phone_number"
})
public class CustomerResponse {
    private Long id;
    @JsonProperty("full_name")
    private String fullName;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;
}