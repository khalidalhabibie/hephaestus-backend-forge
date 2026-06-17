package com.example.training.dto;
import com.fasterxml.jackson.annotation.JsonProperty;



public class PatchCustomerRequest {
    @JsonProperty("full_name")
    private String fullName;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;

    public PatchCustomerRequest(String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    // nah ini getter and setter 
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
