package com.example.main.config;

import com.example.main.security.Middleware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final Middleware middleware;

    public WebMvcConfig(Middleware middleware) {
        this.middleware = middleware;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(middleware)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/auth/login");
    }
}