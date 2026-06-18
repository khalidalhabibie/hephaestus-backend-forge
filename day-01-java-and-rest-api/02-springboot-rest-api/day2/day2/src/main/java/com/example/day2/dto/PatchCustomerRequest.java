package com.example.day2.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PatchCustomerRequest {
    
    // @NotNull(message = "Id tidak boleh kosong")
    private Long id;
    
	@Size(min = 3,message = "Nama minimun 3 character")
    private String fullName;
    
    @Email(message = "Format email tidak valid") 
    private String email;
    
    @Size(max = 10, message = "Phone number maksimal 10 digit")
    private String phoneNumber;

    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
}
