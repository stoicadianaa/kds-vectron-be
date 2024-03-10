package com.dianastoica.kdsvectron.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("*") // Allow all origins
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE") // Allow specific HTTP methods
                .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept"); // Allow specific headers
    }
}
