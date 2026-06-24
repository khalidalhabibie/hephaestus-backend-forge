package com.fif.training.exercisespringboot.DTO;

import com.fif.training.exercisespringboot.Model.Roles;

public record UserResponse(
        String username,
        Roles role) {

}
