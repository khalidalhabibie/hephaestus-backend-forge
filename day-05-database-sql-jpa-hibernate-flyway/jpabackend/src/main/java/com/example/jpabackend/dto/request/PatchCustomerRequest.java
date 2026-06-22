package com.example.jpabackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatchCustomerRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Min(10)
    @Max(12)
    private String phoneNumber;
}