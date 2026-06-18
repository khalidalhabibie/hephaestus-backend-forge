package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class CustomerResponse {
    //disini buat fields apa aja sih yang sekiranya bakal 
    // dikirim ke client setelah berhasil create customer, 
    // misalnya id, fullName, email, phoneNumber.
    @JsonProperty
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;

    public CustomerResponse(){}

    public CustomerResponse(Long id, String fullName, String email, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    //Getter and Setters
}
