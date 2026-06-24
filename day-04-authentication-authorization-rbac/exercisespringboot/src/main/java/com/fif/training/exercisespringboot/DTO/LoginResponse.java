package com.fif.training.exercisespringboot.DTO;
import com.fif.training.exercisespringboot.Model.Roles;

public record LoginResponse(
        String token,
        String username,
        Roles role
) {
}