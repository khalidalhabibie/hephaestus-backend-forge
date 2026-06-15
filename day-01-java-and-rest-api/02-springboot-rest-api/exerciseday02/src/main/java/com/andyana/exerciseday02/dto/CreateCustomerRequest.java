package com.andyana.exerciseday02.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    
    @JsonProperty("full_name")
    @NotBlank(message = "Mohon Isi Nama Lengkap Anda")
    @Size(max = 100, message = "Nama Lengkap maksimal 100 karakter")    
    private String fullName;
    
    @NotBlank(message = "Mohon Isi Email Anda")
    @Email(message = "Format email tidak valid")
    private String email;
    
    @JsonProperty("phone_number")
    @NotBlank(message = "Mohon Isi Nomor Telepon Anda")
    @Size(max = 10, message = "Nomor Telepon maksimal 10 karakter")
    private String phoneNumber;
}
