package com.andyana.exerciseday02.middleware;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.andyana.exerciseday02.model.User;
import com.andyana.exerciseday02.service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.andyana.exerciseday02.model.Role;

@Component
public class AuthMiddleware extends OncePerRequestFilter {

    private final AuthService authService;

    public AuthMiddleware(AuthService authService) {
        this.authService = authService;
    }

    private boolean isForbidden(User user, HttpServletRequest request) {

    String method = request.getMethod();
    String uri = request.getRequestURI();
    Role role = user.getRole();

    // APPROVER tidak boleh create customer
    if (method.equals("POST")
            && uri.equals("/api/v1/customers")
            && role == Role.APPROVER) {
        return true;
    }

    // APPROVER tidak boleh create loan application
    if (method.equals("POST")
            && uri.equals("/api/v1/loan-applications")
            && role == Role.APPROVER) {
        return true;
    }

    // STAFF tidak boleh approve/reject loan
    if (method.equals("PATCH")
            && (uri.matches("/api/v1/loan-applications/\\d+/approve")
                || uri.matches("/api/v1/loan-applications/\\d+/reject"))
            && role == Role.STAFF) {
        return true;
    }

    return false;
}

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        // Login tidak perlu token
        return path.equals("/api/v1/auth/login");
    }

    @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                "{\"message\":\"Authorization header tidak valid\"}"
        );
        return;
    }

    String token = authHeader.replace("Bearer ", "");

    try {

        User user = authService.getUserByToken(token);

        if (isForbidden(user, request)) {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            response.getWriter().write("""
                    {
                    "code": "FORBIDDEN",
                    "message": "You do not have permission to access this resource",
                    "errors": []
                    }
                    """);

            return;
        }

        request.setAttribute("currentUser", user);

        filterChain.doFilter(request, response);

    } catch (RuntimeException e) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                "{\"message\":\"" + e.getMessage() + "\"}"
        );
    }
}
}