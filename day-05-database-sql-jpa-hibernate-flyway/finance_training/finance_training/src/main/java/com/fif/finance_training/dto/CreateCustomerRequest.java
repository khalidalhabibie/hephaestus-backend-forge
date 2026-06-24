package com.fif.finance_training.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    
    @NotBlank(message = "full_name is required")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "nik is required")
    private String nik;

    @NotBlank(message = "email is required")
    @Email(message = "email format is invalid")
    private String email;

    @NotBlank(message = "phone_number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
}
