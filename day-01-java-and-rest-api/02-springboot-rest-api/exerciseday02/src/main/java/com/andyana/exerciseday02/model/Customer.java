package com.andyana.exerciseday02.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}

