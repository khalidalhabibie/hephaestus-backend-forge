package com.example.training.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.training.auth.AuthContext;
import com.example.training.exception.ForbiddenException;

class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void setUp() {
        // ponytail: AuthService stateless enough for manual instantiation
        authService = new AuthService();
    }

    @Test
    void should_login_successfully_when_username_and_password_are_valid() {
        // given
        String username = "admin";
        String password = "password";

        // when
        String token = authService.login(username, password);

        // then
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void should_throw_unauthorized_when_password_is_invalid() {
        // given
        String username = "admin";
        String password = "wrong";

        // when & then
        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> authService.login(username, password));
        assertEquals("UNAUTHORIZED", ex.getCode());
    }

    @Test
    void should_return_current_user_when_token_is_valid() {
        // given
        String token = authService.login("admin", "password");

        // when
        AuthContext ctx = authService.getCurrentUser(token);

        // then
        assertNotNull(ctx);
        assertEquals("STAFF", ctx.getRole());
    }

    @Test
    void should_throw_unauthorized_when_token_is_missing() {
        // given
        String token = null;

        // when & then
        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> authService.getCurrentUser(token));
        assertEquals("UNAUTHORIZED", ex.getCode());
    }

    @Test
    void should_throw_unauthorized_when_token_is_blank() {
        // given
        String token = "   ";

        // when & then
        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> authService.getCurrentUser(token));
        assertEquals("UNAUTHORIZED", ex.getCode());
    }

    @Test
    void should_login_as_approver_successfully() {
        // given
        String username = "approver";
        String password = "password";

        // when
        String token = authService.login(username, password);

        // then
        assertNotNull(token);
        AuthContext ctx = authService.getCurrentUser(token);
        assertEquals("APPROVER", ctx.getRole());
    }

    @Test
    void should_throw_unauthorized_when_token_is_invalid() {
        // given
        String token = "garbage-token";

        // when & then
        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> authService.getCurrentUser(token));
        assertEquals("UNAUTHORIZED", ex.getCode());
    }
}
