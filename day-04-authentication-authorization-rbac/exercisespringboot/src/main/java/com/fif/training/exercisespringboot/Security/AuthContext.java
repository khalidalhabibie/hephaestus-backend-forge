package com.fif.training.exercisespringboot.Security;

import com.fif.training.exercisespringboot.Model.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthContext {

    private String username;
    private Roles role;

}
