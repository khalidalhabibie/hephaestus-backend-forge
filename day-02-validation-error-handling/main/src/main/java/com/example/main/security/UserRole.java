package com.example.main.security;

public enum UserRole {
    ADMIN(1, "token-admin"),
    STAFF(2, "token-staff"),
    APPROVER(3, "token-approver"),
    MANAGER(4, "token-manager");

    private final int value;
    private final String token;

    UserRole(int value, String token) {
        this.value = value;
        this.token = token;
    }

    public int getValue() {
        return value;
    }

    public String getToken() {
        return token;
    }

    // helper: find role by token
    public static UserRole fromToken(String token) {
        for (UserRole role : UserRole.values()) {
            if (role.getToken().equals(token)) {
                return role;
            }
        }
        return null;
    }
}