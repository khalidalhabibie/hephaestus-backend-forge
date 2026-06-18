package com.andyana.exerciseday02.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    
    @JsonProperty("full_name")
    private String fullName;
    
    private String email;
    
    @JsonProperty("phone_number")
    private String phoneNumber;
}
