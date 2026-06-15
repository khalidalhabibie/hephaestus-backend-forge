package com.example.training.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder
public class CustomerResponse {
    //disini buat fields apa aja sih yang sekiranya bakal 
    // dikirim ke client setelah berhasil create customer, 
    // misalnya id, fullName, email, phoneNumber.
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;

    //Getter and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
