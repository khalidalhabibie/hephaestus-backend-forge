package com.fif.exercise02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    public String userId;
    public String username;
    public String role;
}
