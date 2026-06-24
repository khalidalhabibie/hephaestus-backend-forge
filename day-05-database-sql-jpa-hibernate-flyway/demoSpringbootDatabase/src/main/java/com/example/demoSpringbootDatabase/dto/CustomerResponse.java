package com.example.demoSpringbootDatabase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CustomerResponse {
    private Long id;
    @JsonProperty("full_name")
    private String fullName;
    private String nik;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
}
