package com.example.day2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:5173}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                // Public
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()

                // Admin only
                .requestMatchers("/api/v1/auth/register").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v0/customers/**").hasRole("ADMIN")

                // Loan: approve & reject — APPROVER or ADMIN
                .requestMatchers(HttpMethod.PATCH, "/api/v1/loan-applications/*/approve").hasAnyRole("APPROVER", "ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/loan-applications/*/reject").hasAnyRole("APPROVER", "ADMIN")

                // Loan: approve & reject — APPROVER, ADMIN, MANAGER
                .requestMatchers(HttpMethod.PATCH, "/api/v1/loan-applications/*/approve").hasAnyRole("APPROVER", "ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/loan-applications/*/reject").hasAnyRole("APPROVER", "ADMIN", "MANAGER")
                
                // Loan: cancel — STAFF or ADMIN
                .requestMatchers(HttpMethod.PATCH, "/api/v1/loan-applications/*/cancel").hasAnyRole("STAFF", "ADMIN")

                // Loan: create — STAFF or ADMIN
                .requestMatchers(HttpMethod.POST, "/api/v1/loan-applications").hasAnyRole("STAFF", "ADMIN")

                // Loan: read — all authenticated
                .requestMatchers(HttpMethod.GET, "/api/v1/loan-applications/**").authenticated()

                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
