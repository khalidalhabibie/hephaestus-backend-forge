package com.fif.exercise02.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthContext {
    public String userId;
    public String role;
    public String username;
}