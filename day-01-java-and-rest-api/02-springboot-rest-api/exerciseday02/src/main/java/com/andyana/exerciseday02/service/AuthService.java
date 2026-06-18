package com.andyana.exerciseday02.service;

import java.util.HashMap;
import java.util.Map;

import com.andyana.exerciseday02.dto.LoginResponse;
import com.andyana.exerciseday02.dto.UserResponse;
import com.andyana.exerciseday02.model.Role;
import org.springframework.stereotype.Service;

import com.andyana.exerciseday02.model.User;

@Service
public class AuthService {
    private final Map<String, User> tokenUserMap = new HashMap<>();
    public AuthService() {
        User Admin = new User();
        Admin.setUsername("admin");
        Admin.setPasswordHash("admin123");
        Admin.setRole(Role.ADMIN);
        Admin.setActive(true);

        User Staff = new User();
        Staff.setUsername("staff");
        Staff.setPasswordHash("staff123");
        Staff.setRole(Role.STAFF);
        Staff.setActive(true);

        User Approver = new User();
        Approver.setUsername("approver");
        Approver.setPasswordHash("approver123");
        Approver.setRole(Role.APPROVER);
        Approver.setActive(true);

        tokenUserMap.put("token-admin", Admin);
        tokenUserMap.put("token-staff", Staff);
        tokenUserMap.put("token-approver", Approver);
    }

    public String getTokenByUser(User user) {
    for (Map.Entry<String, User> entry : tokenUserMap.entrySet()) {
        if (entry.getValue().getUsername().equals(user.getUsername())) {
            return entry.getKey();
        }
    }
    return null;
}
public User getUserByToken(String token) {
    User user = tokenUserMap.get(token);

    if (user == null) {
        throw new RuntimeException("Token tidak valid");
    }

    return user;
}

    public LoginResponse login(String username, String password) {
        for (User user : tokenUserMap.values()) {
            if (user.getUsername().equals(username) &&
                user.getPasswordHash().equals(password)) {

                String token = getTokenByUser(user);

                return new LoginResponse(
                    token,
                    user.getUsername(),
                    user.getRole().toString()
                );
            }
        }
        return null; // Invalid credentials
    }

    public UserResponse getCurrentUser(String authorizationHeader) {
    String token = authorizationHeader.replace("Bearer ", "");
    User user = tokenUserMap.get(token);

    if (user == null) {
        throw new RuntimeException("Token tidak valid");
    }

    return new UserResponse(
        user.getUsername(),
        user.getRole().toString()
);
}

    }


