package com.fif.training.exercisespringboot.Security;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {
    private static final Map<String, String> TOKEN_ROLE_MAP = new HashMap<>();

    static {
        TOKEN_ROLE_MAP.put("token-admin", "ADMIN");
        TOKEN_ROLE_MAP.put("token-staff", "STAFF");
        TOKEN_ROLE_MAP.put("token-approver", "APPROVER");
    }

    public boolean isValid(String token) {
        return token != null && TOKEN_ROLE_MAP.containsKey(token);
    }

    public String getRole(String token) {
        return TOKEN_ROLE_MAP.get(token);
    }
}