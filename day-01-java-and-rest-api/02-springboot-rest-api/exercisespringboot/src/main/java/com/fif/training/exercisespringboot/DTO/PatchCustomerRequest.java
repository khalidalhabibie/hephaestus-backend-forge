package com.fif.training.exercisespringboot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({ "full_name", "email", "phone_number" })
public class PatchCustomerRequest {
    @JsonProperty("full_name")
    @Size(max = 100, message = "Maksimal 100 karakter", min = 3)
    private String fullName;

    @Email
    private String email;

    @JsonProperty("phone_number")
    @Size(min = 10, message = "Minimal 10 karakter")
    private String phoneNumber;
}