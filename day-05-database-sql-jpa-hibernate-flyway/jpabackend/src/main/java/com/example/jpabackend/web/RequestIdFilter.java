package com.example.jpabackend.web;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


//sebenarnya ini termasuk ke requestId karena harus di generate masing, sedangkan
//correlationId digunakan untuk digunakan di service lain, tapi terdiri dari beberapa req id
//corr id = Order 999:
// req id:
// -tiket 123(check stock)
// -tiket 124(payment)
// -tiket 125(delivery)

@Component
public class RequestIdFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-Correlation-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = request.getHeader(HEADER_NAME);

        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        // masuk ke MDC
        MDC.put("correlationId", correlationId);

        // kirim balik ke response
        response.setHeader(HEADER_NAME, correlationId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("correlationId");
        }
    }
}
