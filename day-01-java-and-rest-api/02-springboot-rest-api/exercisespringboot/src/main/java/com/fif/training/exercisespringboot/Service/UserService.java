package com.fif.training.exercisespringboot.Service;

import java.util.Map;
import java.util.TreeMap;

import com.fif.training.exercisespringboot.Model.Customer;
import com.fif.training.exercisespringboot.Model.User;

public class UserService {
    // Database Using Map
    private Map<Long, User> userStorage = new TreeMap<>();
    private Long id = 0L;

    public User getUserByUsername(String username) {
        return userStorage.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }


}
