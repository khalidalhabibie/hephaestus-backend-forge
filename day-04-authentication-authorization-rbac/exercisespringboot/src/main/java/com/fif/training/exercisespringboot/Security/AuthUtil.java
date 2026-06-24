package com.fif.training.exercisespringboot.Security;

import org.springframework.stereotype.Component;

import com.fif.training.exercisespringboot.Exception.UnauthorizedException;
import com.fif.training.exercisespringboot.Model.User;
import com.fif.training.exercisespringboot.Service.AuthService;

@Component
public class AuthUtil {

    private final AuthService authService;

    public AuthUtil(AuthService authService) {
        this.authService = authService;
    }

    public AuthContext getAuthContext(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Authorization Header Tidak Valid!");
        }

        String token = authorizationHeader.substring(7);
        User user = authService.getUserByToken(token);

        return new AuthContext(user.getUsername(), user.getRole());
    }

}
