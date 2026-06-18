package com.fif.training.exercisespringboot.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fif.training.exercisespringboot.DTO.LoginRequest;
import com.fif.training.exercisespringboot.DTO.LoginResponse;
import com.fif.training.exercisespringboot.DTO.UserResponse;
import com.fif.training.exercisespringboot.Model.User;

// import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    private List<User> users = new ArrayList<>();
    // private PasswordEncoder passwordEncoder;
    User currentUser;

    public AuthService() {
        users.add(new User("admin", "password123", "ADMIN",
                "token-admin"));
        users.add(new User("staff", "staff123", "STAFF",
                "token-staff"));
        users.add(new User("approver", "approver123", "APPROVER",
                "token-approver"));
    }

    public User authenticate(String username, String password) {

        User user = users.stream()
                .filter(u -> u.getUsername().equals(username)
                        && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user == null) {
            throw new RuntimeException("Invalid username or password");
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

    public LoginResponse getLoginResponse(User user) {
        if (user == null) {
            return null;
        }
        LoginResponse loginResponse = new LoginResponse(null, null, null);
        loginResponse.setUsername(user.getUsername());
        loginResponse.setRole(user.getRole());
        loginResponse.setToken(user.getToken());
        return loginResponse;
    }

    public UserResponse getUserResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse(null, null);
        userResponse.setUsername(user.getUsername());
        userResponse.setRole(user.getRole());
        return userResponse;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return getLoginResponse(user);
    }

    public UserResponse getCurrentUser(String token) {
        User user = getUserByToken(token);
        if (user == null) {
            throw new RuntimeException("Invalid token");
        }
        return getUserResponse(user);
    }

    public User login(String username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}