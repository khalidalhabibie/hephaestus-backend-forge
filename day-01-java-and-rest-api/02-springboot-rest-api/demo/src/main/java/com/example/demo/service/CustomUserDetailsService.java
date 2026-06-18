package com.example.demo.service;

import com.example.demo.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // private final AuthService authService;
    private final InMemoryUserStore userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // ✅ Ambil user dari Map (bukan DB)
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return toDetails(user);
    }

    private UserDetails toDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),             // ✅ pakai username
                user.getPassword(),             // ✅ pakai password (plaintext / hash sesuai kamu)
                true,                           // enabled
                true,                           // account non-expired
                true,                           // credentials non-expired
                true,                           // account non-locked
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                )
        );
    }
}