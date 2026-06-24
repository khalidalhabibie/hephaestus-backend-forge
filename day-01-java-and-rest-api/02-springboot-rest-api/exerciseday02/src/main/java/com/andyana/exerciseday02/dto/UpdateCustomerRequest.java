package com.andyana.exerciseday02.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateCustomerRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "Mohon Isi Nama Lengkap Anda")
    @Size(max = 100, message = "Nama Lengkap maksimal 100 karakter")    
    private String fullName;
    
    @NotBlank(message = "Mohon Isi Email Anda")
    @Email(message = "Format email tidak valid")
    private String email;
    
    @JsonProperty("phone_number")
    @NotBlank(message = "Mohon Isi Nomor Telepon Anda")
    @Size(min = 10, max = 12, message = "Nomor Telepon harus antara 10-12 karakter")
    private String phoneNumber;
}
