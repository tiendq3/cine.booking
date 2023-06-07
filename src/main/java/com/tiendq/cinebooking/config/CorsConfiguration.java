package com.tiendq.cinebooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5500") // cho phép truy cập từ nguồn này
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // cho phép các phương thức HTTP
                        .allowedHeaders("*") // cho phép các header
                        .allowCredentials(true); // cho phép cookie
            }
        };
    }
}


