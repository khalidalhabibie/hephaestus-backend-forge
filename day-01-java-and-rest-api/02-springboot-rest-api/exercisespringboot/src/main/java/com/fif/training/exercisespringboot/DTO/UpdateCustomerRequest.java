package com.fif.training.exercisespringboot.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({
        "full_name",
        "email",
        "phone_number"
})

public class UpdateCustomerRequest {
    @JsonProperty("full_name")
    @NotBlank(message = "Nama harus diisi dengan benar")
    @Size(max = 100, message = "Maksimal 100 karakter", min = 3)
    public String fullName;

    @NotBlank(message = "Email harus diisi dengan benar")
    @Email
    public String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number harus diisi dengan benar")
    @Size(min = 10, message = "Minimal 10 karakter")
    public String phoneNumber;
}