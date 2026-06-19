package com.example.demo.service;

import org.springframework.stereotype.Component;

import com.example.demo.model.Role;
import com.example.demo.model.User;

import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemoryUserStore {

    private final Map<String, User> users = new HashMap<>();

    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setUsername("admin");
        user1.setPassword("$2b$10$abcdefghijklmnopqrstuu0SYq8twpcthS10uxQ26rv0s3Obj8Ufu");
        user1.setRole(Role.ADMIN);

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setUsername("staff");
        user2.setPassword("$2b$10$abcdefghijklmnopqrstuu0SYq8twpcthS10uxQ26rv0s3Obj8Ufu");
        user2.setRole(Role.STAFF);

        User user3 = new User();
        user3.setId(UUID.randomUUID());
        user3.setUsername("approver");
        user3.setPassword("$2b$10$abcdefghijklmnopqrstuu0SYq8twpcthS10uxQ26rv0s3Obj8Ufu");
        user3.setRole(Role.APPROVER);

        User user4 = new User();
        user4.setId(UUID.randomUUID());
        user4.setUsername("manager");
        user4.setPassword("$2b$10$abcdefghijklmnopqrstuu0SYq8twpcthS10uxQ26rv0s3Obj8Ufu");
        user4.setRole(Role.MANAGER);

        users.put(user1.getUsername(), user1);
        users.put(user2.getUsername(), user2);
        users.put(user3.getUsername(), user3);
        users.put(user4.getUsername(), user4);
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}
