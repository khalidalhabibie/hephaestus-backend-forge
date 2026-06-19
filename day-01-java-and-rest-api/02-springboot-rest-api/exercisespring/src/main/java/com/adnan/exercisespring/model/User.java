package com.adnan.exercisespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.adnan.exercisespring.enums.RoleEnum;

@Data
@AllArgsConstructor
public class User {
  private UUID id;
  private String email;
  private String passwordHash;
  private RoleEnum role; // ADMIN, STAFF, APPROVER
  private Boolean active = true;
  private ZonedDateTime createdAt = ZonedDateTime.now();
}
