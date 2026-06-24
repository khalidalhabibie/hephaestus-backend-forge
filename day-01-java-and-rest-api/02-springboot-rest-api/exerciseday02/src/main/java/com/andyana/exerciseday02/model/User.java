package com.andyana.exerciseday02.model;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class User {
    private String username;
    private String passwordHash;
    private Role role;
    private boolean isActive;
    private OffsetDateTime createdAt = OffsetDateTime.now();
}