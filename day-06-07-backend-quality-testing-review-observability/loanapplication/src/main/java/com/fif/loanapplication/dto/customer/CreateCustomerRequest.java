package com.fif.loanapplication.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fif.loanapplication.dto.common.BaseDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonPropertyOrder({
        "uid",
        "nik",
        "full_name",
        "email",
        "phone_number",
        "created_at",
        "updated_at"
})

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest extends BaseDto {

    @NotBlank(message = "NIK wajib diisi")
    @Pattern(regexp = "^[0-9]{16}$", message = "NIK harus 16 digit angka")
    String nik;

    @JsonProperty("full_name")
    @NotBlank(message = "Full tidak boleh kosong!")
    @Size(min = 4, max = 100, message = "Fullname harus 3 sampai 100 karakter!")
    String fullName;

    @NotBlank(message = "Email wajib di isi")
    @Email(message = "Email harus valid!")
    String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number tidak boleh kosong!")
    String phoneNumber;
}
