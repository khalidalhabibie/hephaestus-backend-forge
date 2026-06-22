package com.example.jpabackend.dto;

import lombok.Setter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

// @Setter
// @Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCustomerRequest {
    // @JsonProperty("full_name")
    @Schema(example = "Budi Santoso", description = "")
    @NotBlank(message = "full_name wajib diisi")
    @Size(min = 3, max = 100, message = "full_name harus antara 3 sampai 100 karakter")
    private String fullName;

    @Schema(example = "2171100206039005", description = "")
    @NotBlank(message = "nik wajib diisi")
    @Size(min = 15, message = "nik harus minimal 15 karakter")
    private String nik;

    @Schema(example = "budi@gmail.com", description = "")
    @NotBlank(message = "email wajib diisi")
    @Email(message = "format email harus valid")
    private String email;

    // @JsonProperty("phone_number")
    @Schema(example = "082282020525", description = "")
    @NotBlank(message = "phone_number wajib diisi")
    @Size(min = 10, message = "minimal 10 karakter")
    private String phoneNumber;

    public CreateCustomerRequest(
            @NotBlank(message = "full_name wajib diisi") @Size(min = 3, max = 100, message = "full_name harus antara 3 sampai 100 karakter") String fullName,
            @NotBlank(message = "nik wajib diisi") @Size(min = 15, message = "nik harus minimal 15 karakter") String nik,
            @NotBlank(message = "email wajib diisi") @Email(message = "format email harus valid") String email,
            @NotBlank(message = "phone_number wajib diisi") @Size(min = 10, message = "minimal 10 karakter") String phoneNumber) {
        this.fullName = fullName;
        this.nik = nik;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
