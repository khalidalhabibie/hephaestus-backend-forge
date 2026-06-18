package com.fif.training.exercisespringboot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchCustomerRequest {

    @JsonProperty("full_name")
    @Size(min = 3, max = 100, message = "Fullname harus 3 sampai 100 karakter")
    public String fullName;

    @Email(message = "Email harus valid!")
    public String email;

    @JsonProperty("phone_number")
    @Size(min = 10, max = 15, message = "Phone number harus 10 sampai 15 karakter")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number hanya boleh angka")
    public String phoneNumber;

}
