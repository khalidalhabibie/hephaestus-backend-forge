package com.adnan.exercisespring.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}