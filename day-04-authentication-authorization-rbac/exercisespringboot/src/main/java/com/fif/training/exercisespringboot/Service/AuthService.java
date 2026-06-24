package com.fif.training.exercisespringboot.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fif.training.exercisespringboot.DTO.LoginRequest;
import com.fif.training.exercisespringboot.DTO.LoginResponse;
import com.fif.training.exercisespringboot.Exception.UnauthorizedException;
import com.fif.training.exercisespringboot.Model.Roles;
import com.fif.training.exercisespringboot.Model.User;

@Service
public class AuthService {

    // UserStorage
    private final Map<Long, User> userStorage = new HashMap<>();

    // TokenUsername
    private final Map<String, String> tokenByUsername = new HashMap<>();

    // UsernameToken
    private final Map<String, String> usernameByToken = new HashMap<>();

    public AuthService() {
        seedUsers();
        seedTokens();
    }

    private void seedUsers() {
        userStorage.put(1L, new User("admin", "admin123", Roles.ADMIN));
        userStorage.put(2L, new User("staff", "staff123", Roles.STAFF));
        userStorage.put(3L, new User("approver", "approver123", Roles.APPROVER));
        userStorage.put(4L, new User("manager", "manager123", Roles.MANAGER));
    }

    private void seedTokens() {
        tokenByUsername.put("admin", "token-admin");
        tokenByUsername.put("staff", "token-staff");
        tokenByUsername.put("approver", "token-approver");
        tokenByUsername.put("manager", "token-manager");

        usernameByToken.put("token-admin", "admin");
        usernameByToken.put("token-staff", "staff");
        usernameByToken.put("token-approver", "approver");
        usernameByToken.put("token-manager", "manager");
    }

    // Service Login
    public LoginResponse login(LoginRequest request) {

        User user = userStorage.values()
                .stream()
                .filter(u -> u.getUsername().equals(request.username()))
                .findFirst()
                .orElse(null);

        // Auth
        if (user == null || !user.getPassword().equals(request.password())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        // Get Token
        String token = tokenByUsername.get(user.getUsername());

        LoginResponse response = new LoginResponse(token, user.getUsername(), user.getRole());
        return response;

    }

    // Get user by token
    public User getUserByToken(String token) {
        String username = usernameByToken.get(token);

        if (username == null) {
            throw new UnauthorizedException("Token tidak valid!");
        }

        User user = userStorage.values()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (user == null) {
            throw new UnauthorizedException("User tidak ditemukan!");
        }

        return user;
    }

}
