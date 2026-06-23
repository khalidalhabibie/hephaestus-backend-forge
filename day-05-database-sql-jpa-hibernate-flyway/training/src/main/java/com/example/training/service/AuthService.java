package com.example.training.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.training.auth.AuthContext;
import com.example.training.exception.ForbiddenException;

@Service
public class AuthService {

    // ponytail: hardcoded users for training — replace with real auth in prod
    private static final Map<String, AuthContext> TOKENS = new ConcurrentHashMap<>();

    private static final String VALID_TOKEN = "token-123";
    private static final String APPROVER_TOKEN = "token-approver";

    public String login(String username, String password) {
        if ("admin".equals(username) && "password".equals(password)) {
            String token = UUID.randomUUID().toString();
            TOKENS.put(token, new AuthContext("user-staff", "STAFF"));
            return token;
        }
        if ("approver".equals(username) && "password".equals(password)) {
            String token = UUID.randomUUID().toString();
            TOKENS.put(token, new AuthContext("user-approver", "APPROVER"));
            return token;
        }
        throw new ForbiddenException("UNAUTHORIZED", "Invalid username or password");
    }

    public AuthContext getCurrentUser(String token) {
        if (token == null || token.isBlank()) {
            throw new ForbiddenException("UNAUTHORIZED", "Token is missing");
        }
        AuthContext ctx = TOKENS.get(token);
        if (ctx == null) {
            throw new ForbiddenException("UNAUTHORIZED", "Token is invalid");
        }
        return ctx;
    }
}
