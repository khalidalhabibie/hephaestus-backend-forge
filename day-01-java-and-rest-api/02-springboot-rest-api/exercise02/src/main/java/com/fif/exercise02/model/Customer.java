package com.fif.exercise02.model;

import java.time.ZonedDateTime;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Customer {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;


    public Customer(String id, String fullName, String email, String phoneNumber, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public String getDisplayName() {
        return ("Customer: " + fullName);

    }

}
