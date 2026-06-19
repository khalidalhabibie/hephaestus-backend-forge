package com.fif.training.exercisespringboot.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fif.training.exercisespringboot.DTO.LoginRequest;
import com.fif.training.exercisespringboot.DTO.LoginResponse;
import com.fif.training.exercisespringboot.DTO.UserResponse;
import com.fif.training.exercisespringboot.Model.User;
import com.fif.training.exercisespringboot.exception.UnauthorizedException;

@Service
public class AuthService {
    private List<User> users = new ArrayList<>();

    public AuthService() {
        users.add(new User("admin", "admin123", "ADMIN", "token-admin"));
        users.add(new User("staff", "staff123", "STAFF", "token-staff"));
        users.add(new User("approver", "approver123", "APPROVER", "token-approver"));
    }

    public User authenticate(String username, String password) {
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user == null) {
            throw new UnauthorizedException("Invalid username or password");
        }
        return user;
    }

    public User getUserByToken(String token) {
        for (User user : users) {
            if (user.getToken().equals(token)) {
                return user;
            }
        }
        return null;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return new LoginResponse(user.getToken(), user.getUsername(), user.getRole());
    }

    public UserResponse getCurrentUser(String token) {
        User user = getUserByToken(token);
        if (user == null) {
            throw new UnauthorizedException("Invalid token");
        }
        return new UserResponse(user.getUsername(), user.getRole());
    }
}