package com.example.main.models;

import com.example.main.security.UserRole;

public class User {
    private String username;
    private String password;
    private UserRole role;
    private String token;

    public User(String username, String password, UserRole role, String token) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}