package com.example.training.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
}
