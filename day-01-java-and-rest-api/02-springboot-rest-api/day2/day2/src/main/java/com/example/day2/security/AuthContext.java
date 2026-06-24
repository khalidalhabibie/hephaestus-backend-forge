package com.example.day2.security;

import com.example.day2.model.User;
import java.util.HashMap;
import java.util.Map;

public class AuthContext {
    private static final Map<String, User> USERS = new HashMap<>();
    private static final Map<String, User> TOKENS = new HashMap<>();

    static {
        // Inisialisasi dummy users sesuai spesifikasi soal
        User admin = new User("admin", "admin123", "ADMIN", "token-admin");
        User staff = new User("staff", "staff123", "STAFF", "token-staff");
        User approver = new User("approver", "approver123", "APPROVER", "token-approver");

        USERS.put(admin.getUsername(), admin);
        USERS.put(staff.getUsername(), staff);
        USERS.put(approver.getUsername(), approver);

        TOKENS.put(admin.getToken(), admin);
        TOKENS.put(staff.getToken(), staff);
        TOKENS.put(approver.getToken(), approver);
    }

    public static User getUserByUsername(String username) { return USERS.get(username); }
    public static User getUserByToken(String token) { return TOKENS.get(token); }
}
