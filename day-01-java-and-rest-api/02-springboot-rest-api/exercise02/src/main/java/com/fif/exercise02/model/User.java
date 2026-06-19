package com.fif.exercise02.model;

import com.fif.exercise02.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class User {
    private String userId;
    private String username;
    private String password;
    private Role role;

    public User(String userId, String username, String password, Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
