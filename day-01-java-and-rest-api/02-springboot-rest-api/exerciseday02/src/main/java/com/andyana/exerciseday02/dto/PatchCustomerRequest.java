package com.andyana.exerciseday02.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchCustomerRequest {
    @JsonProperty("full_name")
    @Size(max = 100, message = "Nama Lengkap maksimal 100 karakter")    
    private String fullName;
    
    @Email(message = "Format email tidak valid")
    private String email;
    
    @JsonProperty("phone_number")
    @Size(min = 10, max = 12, message = "Nomor Telepon harus antara 10-12 karakter")
    private String phoneNumber;
}
