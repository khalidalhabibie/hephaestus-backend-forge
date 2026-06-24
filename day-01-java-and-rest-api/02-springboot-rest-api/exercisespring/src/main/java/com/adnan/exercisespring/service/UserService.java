package com.adnan.exercisespring.service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adnan.exercisespring.enums.RoleEnum;
import com.adnan.exercisespring.exception.BadRequestException;
import com.adnan.exercisespring.model.User;

@Service
public class UserService {
  private Map<UUID, User> userStorage = new HashMap<>(Map.of(
      UUID.fromString("11111111-1111-1111-1111-111111111111"),
      new User(
          UUID.fromString("11111111-1111-1111-1111-111111111111"),
          "admin@aegira.com",
          "$2b$10$abcdefghijklmnopqrstuu0SYq8twpcthS10uxQ26rv0s3Obj8Ufu",
          RoleEnum.ADMIN,
          true,
          ZonedDateTime.now()),
      UUID.fromString("22222222-2222-2222-2222-222222222222"),
      new User(
          UUID.fromString("22222222-2222-2222-2222-222222222222"),
          "staff@aegira.com",
          "$2b$10$abcdefghijklmnopqrstuu0SYq8twpcthS10uxQ26rv0s3Obj8Ufu",
          RoleEnum.STAFF,
          true,
          ZonedDateTime.now()),
      UUID.fromString("33333333-3333-3333-3333-333333333333"),
      new User(
          UUID.fromString("33333333-3333-3333-3333-333333333333"),
          "approver@aegira.com",
          "$2b$10$abcdefghijklmnopqrstuu0SYq8twpcthS10uxQ26rv0s3Obj8Ufu",
          RoleEnum.APPROVER,
          true,
          ZonedDateTime.now()),
      UUID.fromString("44444444-4444-4444-4444-444444444444"),
      new User(
          UUID.fromString("44444444-4444-4444-4444-444444444444"),
          "manager@aegira.com",
          "$2b$10$abcdefghijklmnopqrstuu0SYq8twpcthS10uxQ26rv0s3Obj8Ufu",
          RoleEnum.MANAGER,
          true,
          ZonedDateTime.now())));

  public User findByEmail(String email) {
    return userStorage.values().stream()
        .filter(user -> user.getEmail().equalsIgnoreCase(email))
        .findFirst()
        .orElseThrow(() -> new BadRequestException("Invalid credentials"));
  }

  public User findById(UUID id) {
    User user = userStorage.get(id);
    if (user == null) {
      throw new BadRequestException("User not found");
    }
    return user;
  }
}
