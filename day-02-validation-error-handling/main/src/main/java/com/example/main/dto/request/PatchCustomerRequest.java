package com.example.main.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchCustomerRequest {

    @Size(min = 3, max = 100, message = "length must be between 3 and 100 characters")
    @JsonProperty("full_name")
    private String fullName;

    private String nik;

    @Email(message = "email format is invalid")
    private String email;

    @Size(min = 10, message = "minimum 10 characters")
    @JsonProperty("phone_number")
    private String phoneNumber;
}