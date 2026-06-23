package com.example.main.services;

import com.example.main.dto.response.UserMeResponse;
import com.example.main.entity.UserEntity;
import com.example.main.exceptions.UnauthorizedException;
import com.example.main.repositories.UserRepository;
import com.example.main.security.UserRole;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final String CORRELATION_ID = "REQ-USER-2026";

    @BeforeEach
    void setUp() {
        MDC.put("correlation_id", CORRELATION_ID);
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }


    @Test
    void should_return_user_profile_successfully_when_token_is_valid() {
        // given
        String validToken = "admin-token";
        
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("davin.bennett");
        userEntity.setRole(UserRole.ADMIN);

        when(userRepository.findByToken(validToken)).thenReturn(Optional.of(userEntity));

        // when
        UserMeResponse response = userService.getCurrentUser(validToken);

        // then
        assertNotNull(response);
        assertEquals("davin.bennett", response.getUsername());
        assertEquals("ADMIN", response.getRole());
        
        verify(userRepository, times(1)).findByToken(validToken);
    }

    @Test
    void should_fallback_to_guest_role_when_user_has_no_role_assigned() {
        // given
        String validToken = "valid-token-no-role";
        
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setUsername("anonymous_user");
        userEntity.setRole(null);

        when(userRepository.findByToken(validToken)).thenReturn(Optional.of(userEntity));

        // when
        UserMeResponse response = userService.getCurrentUser(validToken);

        // then
        assertNotNull(response);
        assertEquals("anonymous_user", response.getUsername());
        assertEquals("GUEST", response.getRole()); 
        
        verify(userRepository, times(1)).findByToken(validToken);
    }

    @Test
    void should_throw_unauthorized_exception_and_log_warn_when_token_is_invalid() {
        // given
        String invalidToken = "expired-or-fake-token";
        
        when(userRepository.findByToken(invalidToken)).thenReturn(Optional.empty());

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.getCurrentUser(invalidToken);
        });

        assertEquals("Authentication is required", exception.getMessage());
        verify(userRepository, times(1)).findByToken(invalidToken);
    }
}