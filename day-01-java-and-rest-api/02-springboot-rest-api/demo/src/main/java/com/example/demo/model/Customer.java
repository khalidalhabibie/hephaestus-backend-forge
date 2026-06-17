package com.example.demo.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Customer {
	private Long id;
	private String fullName;
	private String email;
	private String phoneNumber;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;

    // public Long getId() {
    //     return id;
    // }
    // public void setId(Long id) {
    //     this.id = id;
    // }
    // public String getFullName() {
    //     return fullName;
    // }
    // public void setFullName(String fullName) {
    //     this.fullName = fullName;
    // }
    // public String getEmail() {
    //     return email;
    // }
    // public void setEmail(String email) {
    //     this.email = email;
    // }
    // public String getPhoneNumber() {
    //     return phoneNumber;
    // }
    // public void setPhoneNumber(String phoneNumber) {
    //     this.phoneNumber = phoneNumber;
    // }
    
    // public Customer(Long id, String fullName, String email, String phoneNumber) {
    //     this.id = id;
    //     this.fullName = fullName;
    //     this.email = email;
    //     this.phoneNumber = phoneNumber;
    // }
}
