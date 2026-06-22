package com.fif.exercise02.dto;

import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCustomerRequest {
    // @JsonProperty("full_name")
    @NotBlank(message = "full_name wajib diisi")
    @Size(min = 3, max = 100, message = "full_name harus antara 3 sampai 100 karakter")
    private String fullName;

    @NotBlank(message = "email wajib diisi")
    @Email(message = "format email harus valid")
    private String email;

    // @JsonProperty("phone_number")
    @NotBlank(message = "phone_number wajib diisi")
    @Size(min = 10, message = "minimal 10 karakter")
    private String phoneNumber;
}
