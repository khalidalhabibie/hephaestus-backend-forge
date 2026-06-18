package com.example.main.security;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.example.main.exceptions.UnauthorizedException;
import com.example.main.exceptions.ForbiddenException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Middleware implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Authentication is required");
        }

        String token = authHeader.substring(7);
        UserRole userRole = UserRole.fromToken(token);

        if (userRole == null) {
            throw new UnauthorizedException("Authentication is required");
        }

        request.setAttribute("USER_ROLE", userRole);

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            RequiresRoles requiresRoles = handlerMethod.getMethodAnnotation(RequiresRoles.class);

            if (requiresRoles == null) {
                requiresRoles = handlerMethod.getBeanType().getAnnotation(RequiresRoles.class);
            }

            if (requiresRoles != null) {
                boolean isAuthorized = false;

                for (UserRole allowedRole : requiresRoles.value()) {
                    if (allowedRole == userRole) {
                        isAuthorized = true;
                        break;
                    }
                }

                if (!isAuthorized) {
                    throw new ForbiddenException("You do not have permission to access this resource");
                }
            }
        }

        return true; 
    }
}