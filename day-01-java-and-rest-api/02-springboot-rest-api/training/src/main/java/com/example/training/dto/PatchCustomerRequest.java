package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatchCustomerRequest {

    @Size(min = 3, max = 100, message = "Nama lengkap harus antara 3-100 karakter")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Nama lengkap hanya boleh berisi huruf dan spasi")
    private String fullName;

    @Email(message = "Format email tidak valid")
    @Size(max = 100, message = "Email maksimal 100 karakter")
    private String email;

    @Pattern(regexp = "^(\\+62|0)[0-9]{9,12}$", message = "Nomor telepon harus dimulai dengan +62 atau 0, diikuti 9-12 digit")
    private String phoneNumber;
}