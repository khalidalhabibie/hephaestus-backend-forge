package com.example.main.repositories;

import java.util.Optional;

import com.example.main.models.User;

public interface UserRepository {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByToken(String token);
}
