package com.adnan.exercisespring.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
  private UUID id;
  private String email;
  private String passwordHash;
  private Role role; // ADMIN, STAFF, APPROVER
  private Boolean active = true;
  private ZonedDateTime createdAt = ZonedDateTime.now();
}
