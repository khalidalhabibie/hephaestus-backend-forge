package com.fif.exercise02.dto;


import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PatchCustomerRequest {
    // @JsonProperty("full_name")
    @Size(min = 3, max = 100, message = "full_name harus antara 3 sampai 100 karakter")
    private String fullName;

    @Email(message = "format email harus valid")
    private String email;

    // @JsonProperty("phone_number")
    @Size(min = 10, message = "minimal 10 karakter")
    private String phoneNumber;
}
