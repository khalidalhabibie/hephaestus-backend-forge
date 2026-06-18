package com.fif.exercise02.service;

import com.fif.exercise02.dto.LoginRequest;
import com.fif.exercise02.dto.LoginResponse;
import com.fif.exercise02.dto.UserResponse;
import com.fif.exercise02.security.AuthUtil;
import com.fif.exercise02.security.AuthContext;

import java.util.Map;

public class AuthService {

    // username → [password, token, role, userId]
    private Map<String, String[]> users = Map.of(
            "admin", new String[] { "admin123", "token-admin", "ADMIN", "USR-001","admin" },
            "staff", new String[] { "staff123", "token-staff", "STAFF", "USR-002", "staff"},
            "approver", new String[] { "approver123", "token-approver", "APPROVER", "USR-003", "approver" });

    public LoginResponse login(LoginRequest request) {

        String[] user = users.get(request.getUsername());

        if (user == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        String password = user[0];

        if (!password.equals(request.getPassword())) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        String token = user[1];
        String role = user[2];
        String userId = user[3];
        String username = user [4];

        return new LoginResponse(token, userId, role, username);
    }

    public UserResponse me(String token) {

        AuthContext ctx = AuthUtil.parseToken(token);

        if (ctx == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        return new UserResponse(
                ctx.getUserId(),ctx.getUsername(),
                ctx.getRole());
    }
}
