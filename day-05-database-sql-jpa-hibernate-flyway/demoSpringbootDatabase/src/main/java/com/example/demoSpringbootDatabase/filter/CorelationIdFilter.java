package com.example.demoSpringbootDatabase.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class CorelationIdFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Baca sesuai header instruksi tugas: X-Correlation-Id
        String correlationId = request.getHeader("X-Correlation-Id");
        if (correlationId == null || correlationId.trim().isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        
        // 2. Simpan di MDC dengan nama correlation_id (untuk structured log JSON kamu)
        MDC.put("correlation_id", correlationId);
        
        // 3. Kembalikan header-nya ke response
        response.setHeader("X-Correlation-Id", correlationId);
        
        try {
            filterChain.doFilter(request, response);
        } finally {
            // 4. Bersihkan kunci yang dipasang di atas
            MDC.remove("correlation_id");
        }
    }
}