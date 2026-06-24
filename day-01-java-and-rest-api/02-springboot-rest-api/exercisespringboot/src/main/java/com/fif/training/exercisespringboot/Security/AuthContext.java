package com.fif.training.exercisespringboot.Security;

import java.util.HashMap;
import java.util.Map;

import com.fif.training.exercisespringboot.Model.User;

public class AuthContext {
    private static final Map<String, User> TOKEN_MAP = new HashMap<>();

    static {
        TOKEN_MAP.put("token-admin", new User("admin", "admin123", "ADMIN", "token-admin"));
        TOKEN_MAP.put("token-staff", new User("staff", "staff123", "STAFF", "token-staff"));
        TOKEN_MAP.put("token-approver", new User("approver", "approver123", "APPROVER", "token-approver"));
        TOKEN_MAP.put("token-manager", new User("manager", "manager123", "MANAGER", "token-manager"));
    }

    public static User getUserByToken(String token) {
        return TOKEN_MAP.get(token);
    }
}