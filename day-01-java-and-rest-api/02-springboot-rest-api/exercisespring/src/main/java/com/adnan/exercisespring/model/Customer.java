package com.adnan.exercisespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
  private Long id;
  private String fullName;
  private String email;
  private String phoneNumber;
}