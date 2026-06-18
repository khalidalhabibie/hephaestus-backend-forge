package com.fif.exercise02.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class User {
    private String userId;
    private String username;
    private String password;
    private String role;

    public User(String userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
