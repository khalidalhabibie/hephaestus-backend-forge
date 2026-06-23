package com.example.main.impl;

import com.example.main.models.User;
import com.example.main.repositories.UserRepository;
import com.example.main.security.UserRole;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> userDatabase = new ConcurrentHashMap<>();

    public UserRepositoryImpl() {
        save(new User("admin", "admin123", UserRole.ADMIN, "token-admin"));
        save(new User("staff", "staff123", UserRole.STAFF, "token-staff"));
        save(new User("approver", "approver123", UserRole.APPROVER, "token-approver"));
    }

    public User save(User user) {
        userDatabase.put(user.getUsername(), user);
        return user;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        User user = userDatabase.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userDatabase.values().stream()
                .filter(user -> user.getToken().equals(token))
                .findFirst();
    }
}