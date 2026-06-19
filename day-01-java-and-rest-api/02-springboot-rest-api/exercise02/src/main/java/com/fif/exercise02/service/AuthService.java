package com.fif.exercise02.service;

import com.fif.exercise02.entity.Role;

import com.fif.exercise02.dto.LoginRequest;
import com.fif.exercise02.dto.LoginResponse;
import com.fif.exercise02.dto.UserResponse;
import com.fif.exercise02.security.AuthUtil;
import com.fif.exercise02.security.JwtUtil;
import com.fif.exercise02.security.AuthContext;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthService {
    
private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    // username 
    private Map<String, String[]> users = Map.of(
            "admin", new String[] { "$2a$10$lpcXnZ85rTsDNPCPOF5mFu8vrAgA6jsfkHd6hKxbnwjeYAeblnI0q", "token-admin", "ADMIN", "USR-001","admin" },
            "staff", new String[] { "$2a$10$tawNjd8g3DPaxRBsagnyyeBQMgCoAvYJCxGXYi.lVp6ccDwHTbekq", "token-staff", "STAFF", "USR-002", "staff"},
            "approver", new String[] { "$2a$10$bg6MzGXfdLuG7bkwIjt50eH4/J5UKrJPgrPZ4YhmJAWDOFREq7Lx6", "token-approver", "APPROVER", "USR-003", "approver" },
            "manager", new String[] { "$2a$10$3yG3KVRVW59JYMdYXiBMpeoiMzy44sc99e86HGXiWgqUbzAIppV9K", "token-manager", "MANAGER", "USR-004", "manager" });


    public LoginResponse login(LoginRequest request) {

        String[] user = users.get(request.getUsername());

        if (user == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        String hashedPassword = user[0];

        if (!encoder.matches(request.getPassword(), hashedPassword)) {
            throw new RuntimeException("UNAUTHORIZED");
        }    

        Role role = Role.valueOf(user[2]);
        String userId = user[3];
        String username = user [4];
        String token = JwtUtil.generateToken(userId, role.name(), username);

        return new LoginResponse(token, userId, username, role);
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
