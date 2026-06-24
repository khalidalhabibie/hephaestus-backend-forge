package com.fif.exercise02.security;

import com.fif.exercise02.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthContext {
    public String userId;
    public Role role;
    public String username;
}