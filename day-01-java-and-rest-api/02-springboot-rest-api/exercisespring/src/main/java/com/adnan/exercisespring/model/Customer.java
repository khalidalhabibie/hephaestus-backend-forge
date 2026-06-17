package com.adnan.exercisespring.model;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
  private Long id;
  private String fullName;
  private String email;
  private String phoneNumber;

  public Customer(Long id, String fullName, String email, String phoneNumber) {
    this.id = id;
    this.fullName = fullName;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
}