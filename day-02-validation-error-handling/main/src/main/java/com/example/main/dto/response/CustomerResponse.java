package com.example.main.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponse {
    
    private Long id;
    
    @JsonProperty("full_name")
    private String fullName;
    
    private String nik;

    private String email;
    
    @JsonProperty("phone_number")
    private String phoneNumber;
}